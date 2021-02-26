package com.example.wac;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Img extends AppCompatActivity {

    private ImageView profilePic;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private TextView displayURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img);

        profilePic = findViewById(R.id.profilePic);
        displayURL = findViewById(R.id.display_imgUrl);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePic.setImageURI(Uri.parse("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxITEhUTExMWFRUXGBoYGRgXGR0eIRgbHhsdIBsfISAaHSggHR0lIB4YIzEhJSkrLi4uHSIzODMtNygtLisBCgoKDg0OGxAQGy0mICYvLTUvLS0tLS0tLy8tLy8tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAKsBJgMBIgACEQEDEQH/xAAcAAACAgMBAQAAAAAAAAAAAAADBAIFAAEHBgj/xAA/EAACAQMDAgQDBwIEBQMFAAABAhEAAyEEEjEFQRMiUWEGcYEHFDJCkaHBI7FSYtHwFSRy4fFDorIzNYLC0//EABkBAQEAAwEAAAAAAAAAAAAAAAABAgMEBf/EACsRAAICAgICAQMCBwEAAAAAAAABAhEDIRIxBEFRImFxE/AyM0JSgZHRFP/aAAwDAQACEQMRAD8A7jSlzUSH8MB2UxtmM4MTHoaPacMJE/UEfsaS6hrntvaVbL3RcfazLEWhE7mkjHb1oQNqtdatbBcdU8RgibjG5zwonk4OPY1ms8WB4W2dwnfMbZ80R3jiiXLCNt3KG2mQSJg+ong+9L37Tm7bZbkIshkidxIxJ7RVQY6TAzSGu6jaS5astdCXbpJtriX2ZYCQe3NPE8YpYaJN29huYEspaCUkZCnsPaoBot7VulbK3N7lmUoY2ACCMZk980t0j70Ff7z4RbxG2eFMeHPknd+aOfegss6X1un8RGQsy7hG5TBHyPamKBq9Kl1Gt3FDowIZWEhgeQR3FCm7KbVVZJgASeT/AN6NWVT/ABH123pLFy8w3m2F8ikbiWYKgzxLGJNXsdFxWV57R9U1Ze34untJacEl0ultg2kgklFXJgQCeZ7V5f49+0+3o2S1pQmouzLw0qijsSv5j6dhz2qqLbpEs9t1frul0oB1F+3Z3fh3sBPyHevMdb+1Pp1hf6d37y/ZLOf1Y+UD/cVwz4l65d119797DNACjhF7KO8c575NIC2fQSOPQ11Q8Vds1vIdJ1f2xdQBBXTadEbKbxcaRJHIdZyD2pY/bT1Ec2NL9Fuf/wBa8HA5HBA5x8/3rCldH/lhRh+oztfwb9rmn1Hk1e3T3Ozz/Tb6nKH2OPeukWb6uoZGDKcgqZB+oxXyVpgqgk2w+5WEGfITw4g5I9OKtvhf4m1WgcPYc7J81tvwP8x+U/5hkftXPPxfgzWQ+pKyvK9J+PNBe04vm/btY86XGAZD3Ecn2I5oFj4k1erXfoNMnhZ23tUzILkf4EVS22cbmj5VycJe0bLR7Gsqm+HOs/eUbchtXrbG3etEzscQee6kEMp7girmo9FFbeutNdayHU3EAZkByoPBI7A01So0dsXDdFtfEYBWcAbio4BPJHtU11ClymdwAY4MQZjPHY4oAqPPaKXe+wuqnhkqVJLyIBHaOZNAv663bvpaO/fe3EQrFfIBMn8K4+U0yNrhW8wGGHKn6jB+hqkEl0moOo8Rr8WgGUWVQQ0xDFjncM8YzVoG9qrrHT3V77G/cYXSNqmIswseSBOTnNa6ToLlpCty+947pDMFBUQPL5RkTJkyc0YHL9xgAVQsSQCJAgE5OfTmpLdBJAIJXBAPBiRPpS3TOoLeQuquoDukOsGUYqfpIMGmFsqpZgoBbJIGT8/WgIareU8hVXxG4SPfgicTUdXpd5RtzrsbdCmN2OD6j2oOtuX9y+Ettkhi24wZjygfM96at3vwqwhiswJIEROYjk06Jpit+6tyxJDqLixtMo0twP8AK04qPw/fuPYQ3bLWHiDbZgxUAwPMOZABpu8YG5VLEflB5k55IFR1950ts1u34rgSqBgu4+knA+tPRfY0DWUKzfVpAIkYYAg7T6GKyoU0GDg7TIyJB/kd6rNa13S6b+jbfVOsAKXAZpbOTjAPtxTvTenWrCFLSBFLM5A7sxljn1Jqeue4EPhqrPGAxgT71V8EfyatElm/ECAogjyzBPlMZ5g/IUj4wsXrdpNPcbx2d3uqJRGAnzEmRu7ACrJmbbIALRxMAn5x/FEDg4kSKADq3KqWAZtonavLewnvWXrK3EKOoKspDKe4Igg/TFJLr/8AmWsHjYGXyNzndLfhI/DAjsfas0Gidb968dQ1y3c27LRjba2iDtI9eT70arsJj2osbkKBikiAV5X5UPRlwpDg+U7QSQSwHDGBgn0oOo1IW/aTwnJZX/qgeVIjykzy2IAB4PFWNPQBWrm4AwRPqIP1ByKjptOttdqiBJPJOWJJ5PqTUU1iG4bQbzqAxHseDTNQJlZprupNy+HS2qAjwGDElxtzuEeWG/Y1xb4/+Prl/wC9aPwVSL4QXVkMUtPwRHO9ZBnANdysJc2newJ3EgqI8s4GScxXzp9qFvb1XVyoUbkIxEg20z9TOa3+PFOdMwm3RRdV6lfvgeNfuXSQMOzQMxEHy8ZkCk1ADeg7D2/msUkwTwMD/X5R/entHbQuviEhCRuKiSB3getelCCfRpboWNuaJbXtxTL213HaZWTBOCR2+vtWGzP+tdCh7NTkBFkelMajTDapUPBWCWEDf+YKRyBj3otm1kZjIz6e+PSj3GcqLZYlFJKjMSeSPnis+CMeZUpMcD3rb2vT608bAod1Y5MdqnDReXwLMmM/Svob4C6zY1GgthCB4VtbdxZjYVWD8gYkGvn0mJnt6e9FsMwO0ObavCuZIG0nO6OVHMVz5/HWWPdUbITcWdy+Ctf0xL13T6S+bl1oZi5Y7wo2gKxAVgogeX555r2jERnjvXzL8Q6H7jrQli+X2BWW6sAhioJjacRnBzFehX4t6r1SOnptXfh7ltSCU/MXMwo9QInjvXnZPH/qT19zfGfo6BqPjnUXXdem6FtYlslWvG4LdssOQhI88eoq7+D/AIkGttvuttYv2m2XrL8225HYSpGQaQ12rTpOm0tizYNxC62R5toBKk7mMHzMQYHdjFU3UNbt61oXsKRc1NgjVWu62wAUZx2ZSSJPyrnpPo2WdDu3QoZnICqJJPYDkmtaLV27qLcturowlWUyGHqD3FSu21ZSrAFSIIPBBpbS6JbIi2NttUAW0igKsScACZM1iCetS4VPhlQ2I3THOZjPE0wWiJ71CxdDqGgiQDDCCJ9R2NL63U3Fa2EtF1ZiGfcoFsbSQxBMsJAEDOafYfcnea4rIEtqyljvO6NggmQI8xLQIxzPaKndBJC7ZUg7juiPSIzmTkERFR0fi+GPE2eJ32zHPvnisvaxFdLZJ3PO0QcwJOeBVrYsrfhjSaezYNjS7glt3WHLGGmTlzJEntinNF1W1du3bKNNywVFwQRBYSMnBx6cUPR9UD6i9YFq4ptbSXZYR9wnynuR39MetPWggLbdsk+aI5gcx3iOaMIhrfE8N/CCm5tOwNgFoxMdpqFhLjWk8UhbkKX8MmNwgsATkrMjPIot61uKncy7TMD82CIOOMz9KW6rEJIumbi//TnBnlo/L60S2GM2dMiFmVQC5liB+I+prKHpdVva4ux18NgsssB/KDKn8y5ifUEdqyoUld8Tem0rs828EGTjy7SDAzzM0tpdKv3i5eF12JVbbW98ohXMhezHdn6fVjVXHAHhqHO4AgtEL3PHIHamQKEMBpK3pbVu49wQr3Y3En8W0e57ClLOvZRcvX/6NpfKFccZjduB4aVxFWV3TIxBZQSswSOJEGPTGKvWid7Ni4rLKwwPpkEUDS6VLFvbaQ7RJCjkk5P4jz8zW003hhEtBURTlY/LBwsHBmKhc6im1ygNwo2xlQSQ0j+0zTfofkzWaIXHQuoKod4yZDjjjBETg0PQ2Sl26N6FWIdUAO5SfxFiWMhjxgRVlVL0zrGmvXrqoIu238FiyxuKjdAb8wAziibqhWy42CZgT60DXJcNtxaZUuFSEZhuCt2JEiR7SK3q9bbt7d7BdzBVnuT2pmpRdA7UwJIJjJHr3riH256q0dXZsi0viBAz3PzEebah9R3z/Ndzr5++2fRN/wATLywVrNvaRGY3BvrIH7Vv8dXkSMZ/wnhHx3G0zM9hTGnbIAIIz/2+dRs6IBtxbfjAI/D/AAfnTaaRCZ2iT7CvWxwl30cspRRuxbMmabS1UtPYAwKubHT0Nk3PEAYMAE7ketb+VLZzSl8FYtmpNbgTBPyp1bNY+GUcT3jHB/fFYudGlStiLacMP7EUmziSu0s4wYXH6nA7d6sb2LRgw23d8gTJj5CmLelVRABjPfJPqT3NYcrejNTrsqNH0u47BVG5j+FV7d/rFDv2CpIIgjBq9NzY4e1uQgD80mYyZgcntVdqFJJJyTzWyGjNZLM6XYCg6kXEW5ZZXVHE7yDPHcetPdT+03qW9bqG3bRSrFLaAbwDkMWBJBEjBETVAMiT7j9P4oWns3LjhLVtrrMYUKJJ+Q7x61oz4oSVs6oTd0j6P1DffdPYa0tm5YvbGuLdBINoqSdsfnB2xNH6Z0PS6Te1m0ELZd8szR6sxLED0mq/4I6E+m0ulS6zC5btFWQN5ZZi0RwSsxNeorxHrSOsS1+mN1V23GtwwaV7j0Psalc1UXEt7HO8Md4A2rtjBMzJnGOxph3CiSQB6moXLCsysQCyyVPpIgx9KWKNpZUMWCgM0SYyY4k94k/rW8EZH61uRPvH7UrrtLbfYzkjw23DzQJ4z2I+dRBhVdQdu4SZIEiYETA9BI/UUbbmaq7vTCHR7DLbm5vuyu43FKwVBJ8knaZHpUtB96F6/wCMbXgyvgbZ3AR5t8454j39qoQfqGpa2oZbT3SWVdqRIDEAsZIwvJ7xSfWdbp9OvnuCw2ocW1cDJuMIU8c45OMc05dS74m4ONm0jZtnzTgzPHtU7Fpii+LsZxBMDG4dxPFOtk7BaPQBCHLM9wItssWOQCTO2doMnmJpkXlJZQw3LEgHInifSlunoZuHe7A3DAcRtjBAwJX0NPQKPsq6NispazqFcsFnyNtOCM/Xke4rKlFAdN0txDcNwoWZ5BVYJUABdx7kDFT6drvFDHY6bWK+cRMdx7U7S7Pu3oCVIxMERI5BIg89pq3ZjVERq7bXGsh18RVV2XuFYkAx6Eq36UtY0moGpuXGvhrDIoSzsA2MPxNumTOOf/LektFFVSxcgQXMSfcxUL+p2MgIZg7bZA/DgnPtiJpXwUNaZtoLgAxkAyB9SBQtWrC2/hbVcqSpbgNGCY5Ax+lFvWg6lWEgggg9weartT1IWVvvetm3ZsqCHHm3rtkwqyw2nEEZ+VQEtBqimlS7qblqRbDXLimLfElgW4XvmmbmpQYVk3spZQTG6Bz6xxmo6fVWboKKVbyglP8AKwxKniR2NFu6ZGyVUmCskZg8ifQ1fyPwaRVuKrMEbhgRDAH1U/2NFusQCQJMYHqfSkeh6NrNlbRW2oSQq2hCqs+UQfarEmnsIW0N12tqzp4bEZWZ2/Uc1yT7b9KPvOmfgPbZSfTawP8A+1dkNco+1fomoGksXbl03zZZxcuFQpi4w2nauIEBf3rf40ksqZhkX0HNDqQbaJsQbC3nAy0+vy7UdbJABOARIkc5j65oO1IXbMx5gYiZPEciI57zTCQSFP0Br3OkefNm1uSpIjBgieBMf96tLYzHoKV+4qy7RjGCMEfpUNFqMuv47qsd20HzRAUdws9/SM1zzm4vZolUlouLKg8Zreo0oIyrMcbYMAEGZNMaLTMJLEEkzA4H17/P2p5bdRu1s4pZOMtHnL3R8RstkxyMfqIIb9vpSmi1DBmtuDuDhII7bAQQRg/9wTXp7zxMq0DlhwPnmR+mK8trH/5hrKsLnilHViJCkKVchlIyoCHtmtUmotNHVgm8iakWVy3SN+3Vr4G0RJb3PeljAIJAMEGDwc8V1RejCEtnm7wZFJhY5jccftXUfhO9p9B0ltb/AEzqXVhMyS8kJbHeOCR8zXg+oqHZm2BQSTA4WewpzofwbqNau+wbRCttbcxBQxyRHp6VpzQTjUpUj0cM96Wyw+F/tL1ZunTa27NtwoF9FCPbJPOBtKzziQK7nbGBmff1r5t6/wBHRuoDRadfNbW3pWfA8S6SS7x6DdHyWvo/TWtqKsztAE+sCK8zNGKScfZ2Qu9k7iAiCAR71oXJAK+YHiDzQ9W4VGYkKApMkSBjkjv8qqNFd1NvTaeAuqclA7JFpQjHLhTiFWPKMnt6VprRnY/1Dp3iZRvCuHaDcUDdtBnbPpk0XqGmtXLT27wVrbKQ4bgr3n2or3gqlnIUDuTihq3iW/PbIDSCjxxJGYJEEZ+RoCFmyysWDza2KFthR5SJ8wIyZECPb3pbpGuXV2Rcazct+c+S8u1gUaAY+YkH+aZ1xCW93nAQg7baySB22gEkewooWWDBzABG3EEyM8TIgjmMn2qAjZ1yOXVGDNbIDAdia0xFszDkuwGJaDETH5RjPal+pXXtLvsWPFdnQMAQh2kwzEkZ2iTHJ7UUWHF1rhuE29oAtxwZ5mromxq6SOI4OD3PaqvoXUhqA1xXwpNp02kBLqEh4YgFuwnjFWbuvlkjJxPc+3vQbRLG4rW9qzAMjzgjJxkZkUXRfY2KyltFbQIEtkbU8uDMRiPpWVCgLmhm+t/xbgCIyeGG8hkg7ivdhEA/OmrN9XUMpkMAQeMH50HT6Z1uXGNwsrRtQ8JHp860mkYXmueIxUqF8PsI7j35q0jG2AsdWS6l1tMRea0zWyoMQ68rJ+fPFWIbAnFQZlRWaMCSYHpz9aS6dr01NlL9reEMsAVKlokQQ4kZ+XAoUYezcN1WDxbCkFI5PYz2j0rZvf1dmxvw7t0eXmIn19qBpb99nUm2FtG3J3N51uT+GAI2xOZ5onUdWtlDdcwojccnHsBTbdE9WGt2EVmcKAzRuIGTHEnvFYbYLBpMqCIkxmORweOfnVd1DRi74WoUuWtAvbQXCquWWIcDBxxIxNN9M1CXE8S2SVYk5nkYMbuBilOrGujL0XQVW4ylHG4rjIg7cjIINOUHUsVG4AtH5ViW/UgUcUKBuqSVhogyRA8wg4zx2OPSvM/abqkTpmo3ZDKEHuzMAP8AX6V6uuN/bV8SBrlvRWzu2HfdA/xR5R9AST8xWzDHlkRjN0mc6svAAGTMR/r9KsLFp5kwQDgZEYjnv3qtsowYORg4gAmB/J9ask1IjGW9Iz+le299nnZNdFrpVbuR9BH8mhaS8tu0MgMXYuSYlg53H6nAodnUtuKLBeRPogImT6+gHeKL0rpSb2cyyztAbMwTuYzjLE/pWibbejmaUU+RbaLXIbhXeGMKVC5JmZx9Ku9FpZMscn9FHoI/c96T0iKv4VA+QA/tXqOl9Kd4IEqeGHH/AGPtWEnxX1M4XGWSVY0Vmv0BTDgEESDyCPWvJafR2r2obUBF2Wh4VogCGIJ3sI9CdoPsa6H8baJW062N0EoylhyAwjFc4tfDqadALd/VQOyuP2XbHvWqEnKnR0rHHFyjypv9v/hYXV5qqa4GmO3+k5pN9LeD+JavsXJhrd8YaOOAIMcGOKWudWa25F+01vdHmkMsxHPYGO9dCy/OjbDB/a7HB1JgGs/lJDFSMEjEz3iar73xHqNGj/d7rWi5E7e8TGfaTW9UHuHb5VPKgDcT74PfiKX0HTb2r1unsImQ+95yoRWG5j22xjPrFM00sbOzBH6ke8+yToNzUXW1l4lreTubm7daQWn0AZuO5HpXZlAUQOAKT6TfsPaU6cobWVXw42jaYgRjBFS1WpdQ+207lQpEFRvkmQJPIiTIHIia8mc3JnekkhsiR86BfuFV8u0EkABjAknjHemJqDoDyAYM59RwawKA6hrLdpN90gLIGROTxxTU4xUXAjIkUK4H3LtKhc7gQZOMRnGaoJ2nJUEggkAkGJHtgx+9A6c90rN1FRpOFMiJwfrzRreoVmZQfMsbh6SJFK657u+0qIShYm44ZRsCiQIYHcGPlxkU+wC63Wi3slXbe4QbFLQT3aOFHc9qbNLaS47KC6bGz5ZB74yPXmhC6yXCHKhGKi3EySQZB7fKlCwWovA3oeydltPEF9tu0NJBUZ3BgBMxEGnbN1WUMp3KRIIMyD3+VQtaZVDKFwxLGTMljnn+3FB13TLd1UU7lFt1dQjFcrwPKRK/5eDTQC6HQWrIYWkVAzM5CiJZjLH5k1la0PjQ3i7J3Hbsn8HaZ71lQEdVfuK9tVtllYnc0/gEYMd81rSaEW3dg7lW/ITIUySSJyCZ9YwIipnXJ4osz5yu+IP4ZjninKrCNTSvUNWlpdzzEgYBOSccUO9oEa6L+weMiMiMZwGgkYPBIH6VvSaoMWQkeIm3eBMAkT37GiDZK7rkW4lonzuCVEHgc/Kl7OjvC/dd72+y6qEslVhCJ3Hdy273/wBIZvX4dV2MZBO4DCwO57E9qj03WeNbW5se3u/LcXawyRkdqBG9VrVtm2CHPiOEG1S0EgmWgeVcHzHHHrUtbqBats+0sFEwokn5CpJv3Nu27MbYmfee3yiss3luIGQhlPBHBoCVi7uUNBEgGDzn1pH/AIi33nwPBfZ4ZfxsbNwIGz13Zn0pvSaZbaLbQQqgACSYA4ycn61O6pIIBKn1EY/URUKEArl/xV9lhv6m5qNPeVDdO51uAnzd4YcA+kYrpGtvlELBGciPKsSZMdz25raOSWBUqARBJHmwDIgyIMjMcelZ48ksb5RMZJS0z5s6vo20165YcCbZAbaZEgAkgwDGfT9ahquqMVQ7VQBQoiAW9Md3M5J/irX7R+hnT6x0N/xPF3XWxDKGYwCeM5yOw7VUaJiQoJLFB4azkqikwo9sn9a9mMnkSa/2cM4qPZY9G6U77zvZiRveGCiAIxgHj3q20qqoCqIAwBVVbeBP8+lN6a9IB4nP0rLgovRwZrl2XVq5Vt0/qr2vwsR8jVLptagtsmyXMEN/hFDt6kESKwcVLTRzcZQdxZea7qRucmqHqDEeffsgHgTu9iCc+0QfeiG9QLtyeaKFKkI3ytldf0niqr3CGbymBKgCQSMGT9T27UnrtMF8yrKkQ6c7l9QD3H75q0a4O/GePX/zS11G2l9p2zt3dgeYn1rJY1R1QlI8zY1It3QgZ1Xm05kFfVZYcen0r03wj0LqGsTUjT7FsZV7pENf2ifBGYKzMnGTkniqPqxVlKvweD6Ht8qj8I/Guq0lo6e039NyxK8EHBlWBDLMZ7VyZ1KLUE/38HqYaa5HY/g3XvotDZsto3tC2JcOyKx3Od7IkksF3AmY5xNdCrwvwb8ZaTqhCXLSLqLR3i28Nx+dCROJ+Y/evd1587va2dMQdy2GEEAj0PtkfvSPSNddu+J4lhrOy4yLuIO9QcOI/KasSKFqHKiQpYyMAgd8nOMc/SsSifVluf02tKzFXEqH2gqQVO7/ABATMeopzUFgrFQC0HaCYBMYBPYT3ooM0hrr11GtlQDbmHgEtmAsAdgckntVW9Eeth9Izm2huKFcqCyqdwVoyAYEgHvAmtWdZbdnRHVntkB1BBKEiQGHaRmh9Ua8Emzs3SJ38bZzx3ipaTQ2kZ7iW0VrhDOyqAXIEAk96Vqxe6AK2q8FiVtm9naASFicSeZj96sEmBPPePWq+31IXDFtLjAXGtOdpXYV5PmglfQiQalrLmoFyyLSW2tEnxmZiGURjaAIJnmTRkQXp2kNpSpuXLkszbrhBI3MTtEAeVZgewph7kA9yBwKJVfY0lrxWuIfOfxhWwTAEsONwCgTUKwug1ZuW1coyT+VhkVlGW+slQwJHInI+lZVID1ukW7be224B1KkqSrAEQYYZB9xQTpnVbSW3gIVDb/MXUCIk5k/4qk2mfxxd8VggQqbUDaWJBDkxMgCMHvUheLOybWEKCHjymfT1IiiKwOkuM9x2a3ct+GzWxuI23F8p3gKTjsJg4NMancFZkXc4UlVJgMYwCYxOBNb09t1RVLb2AgsQBPvAxS+t6mqWrtxQ142gdyWoZiRnaBP4vagD21L2wLiAFl86TuAJHmWYyORMZob61VupZ2tLKSCB5RHYnt3/SmLFwsqtBEgEg8jHB96Hq3cCbahzjylts5E5gxAk8UQZO7cgqIJkxiMYJkyeMRj1oWuW54ZFkqH/LumOe8U0TSuoa5IVBEz54BCxGCNwJnMRxGaINEtRcK2ySYMchSYJxIGZz2oqSAAcnuf5pfqGr8K277HubVLbbYlmgcKO7HtSmq6mP8AlwHS095hCXcMwjcygT+MD/fFFsFs09q3WUO7bmMkQQcd/Y+1Qp5P47+ENNq7Vy66kXktNscEj8IJAIBgj5+tcB0lwiCfzCf/AD9Ir6c6Z1C3qbbMgbbudDuUqTtJU88j3Fcj6h9kOr8WLT2Wsg+UuzBo7BgFIwPQ59q7vEzqNqT/AAaMsL6PG6e4WWOAR35/39as03NIRWZvRBuP0A5roHQvsoRIOpvF/wDJbG0fVjk/SK9907pdmwu2zbW2P8o5+Z5P1rdk82K1HZoXiuT3o+f1vlB33kZGe3bPpEcUW3ebgEGZJg4E55if9nir77VNB4OuW4oxeTcOwDKYefn5DGe9eTR9luFPEZ9c5rdjlzjyObJi4ui7tszYAJgEmOwHJ+VUPxB1zwRtUecgFZGInNMWdVyQ2OMV5v4tvAtbHcAn6GP9KeRJwxuSJ4+FSyJNHo9N1BbqB1PPI9D3BrLupO3bJ2zMTifWPWvH9C1nhuRkhsGOx7GvRG9BBgGMweD84rPxsn6sL9m3Jg/TnS6NNlgMZMSe017nrf2NG3N/S3zcYAsbbgDcY/Ky8H2I+tc+c1237JPiB9TpmtXTuewQu48shHln3EET7CtPnclxkvR0YK2jmH2cdK1l7Wi7pQo8Dzs1wkLJBAQlQT5szAwK+ipOP3rivUdN1XpGovnR2TdsX2LLFs3I5IkJ5lYSRnBoel6P1X75oNXq9QVe9qAvgl4YWxlvIPLtgQQOJE81w5lzfK9G+OlR29WBEjIrCwie1Rt2woCqAABAAwAO1R1GnW4hRwGVhBHrXMbCKhgx/DsgREzMmZ7Rx+9ZcsSTJMFdvJ4zPsDnnmofe7aOlmQGKyq+wo1myFECYknJJ5MnmqRC3SOmpp7KWbe4oogb2LHJkyWJJyTRdK7HduTZDEDIO5RwccT6UhrLWrBZrT233XLcLcBAS3/6mVkloyJxIpvTaibly34dwBNpDsPK+6cKZk7YzIHI5oAtrUJcUlGVhJWQZEjkYqF64LSCEYgbVCoJjIHHoP7URbIRSEUDkwMAk+sep71HQ3mZFLrscqCy/wCE9xU0AGzUfeJ3W/u/hxtg7/EnmZjbt+s1u1oRbctaVR4j7rkznByPeY/enDcAicSYE96ipbcZA24gzk+siMdu9XYoHZ01tXa4qjc8bmHeOJrVQ6V0u1p1ZLS7QztcIkmWcyxyTyaygCX9PvKHcy7W3QpgNgiD6ikNRqrw1KqNh05Uo23cbi3sFZjyqm2cnuR7U+Dc8Q4Xw9ogyd26c4iIiKItpVkgASZJA5PqfU0H4Iu52EqNxgwCYk+k9qFpdOiAsECs5DPt7t3z3qfiHftG2NsnzZBnHljg5z7UO/dRHEt57nlUEmCQCcDgGJ+dNkN2bd3xXLOptEDYsZB7yaT1BuJet2bemVtPdFw3bgYLsY5ysS28lsjucx3dW8t1G8O56ruWDtYc8yJHvStjrdhr16wH/qadVa7II2hhIJJEcCcU2VB2hhstXQvhsobbDQBBKGeCRHvmmrl0KCWIAHJJiKrXsJftB9Pd8MXGS74tkKfEAg5JBBDAAE8xT2r0qXUKXFDKeQe/en5H4DFsTz8qXv6YM1t4EoSRKgnIIIBOV7ZHpFb0tgoCNxYEyoIHkEABRAEgR3k55ol9WKkK209jEx9KgC1F2A5MVKl9ZpEursuKGWQYPqDIoijFZWhW6AysrKygOdfbRpQdLauEA7LsfRlP8gVybp+i8W4lpAJYgKCYAP1MV2b7Yf8A7a59Llr/AOQrhFu9nB98V6/hO8Vfc5My+os9WptuyNEqSDHqK8n1t5vH5D+1Xj3aourr/Un1Aq+b/K/yPHSUxbT3ijBh2r0puTXlqtOn6uRtPI49xXN4Wbi3F+zdnhey41GqDIi7FUrI3KMvJnzGe3FN/DevezcLW9c2icwN2zcjegczCiY8xUxPzqjutBBnFWfwqdG+qt2taCbN3+mWViuxmI2sSO04z6+1dOeS4NM0wTtHUPsi+KNbq7923qNQLq203RsTLFtuGQAbRE8Zmr7pOiv3epXLmrW4xsNc+7/01W1bRtoUh4m5cYFsT5YMgSKc+DPg7R9OuXRpxc3Oqy9xplZMBfYHnHcV6vM+1eTKVu0jqSAarVFHtrsdt5IlRIXHLegpqspfVo5RhbIVyDtJEgHsYqAW6hZJeywuugV8qqgi4CCNrYJCzBkEcCa0bGo8OBeXfvB3NbkbN0lYBGdvl3duc01pEcIouEM4A3ECAT3MVX6N9Z94Zbi2vu/hrtdSdxuT5hBOFj58UATqvU0stYV7gTxbgtqCpO8kEhRHB9zTt+2WAhisEHEZg8ZHBoeuBC7lt+Iy5VZAk/M4B961/UFwsWXwtoxBkN3M8RFCMO90AqM+YwIB9Jye31pfTa+zca4tu4jNaO24FIJQxIDen+/SmNPeV1DqQVYSCO4pS5oQguNp1tJduEMzFcORAltpBY7ZAz6VDIxdGXcm9scLcDWYUgoNo5JJlp3ZEYjFTN+74wTw/wCntnxJGG9IqFvqE3vCVSwAO5wQQjD8p7gwRTOpvhEZyCQoJhRJMegHNXa7MdPoHc6haFzwy4D7d23/ACzE/rWqyzYVj4hzuVYDKPKOfTdmcgntWU0XY7QLatuYkgqY2iMjGZM5z8qVu3r2+4BZBVUDI28ed8ysR5YhfMeZ7RlrTsxVSw2sQCRMwe4moBDqmnIFy5ZNtNSybLb3OJ/KDmSJ9P34qZu3k0wd0F2+qSUt4DvGQu84kzEmpf8ACrRMsC/9TxRvYttaAPLJ8oxwMc+tMLabezbyVIACQIBEyZ5zj9KvoG9KgCyE2bvMRjk8zGCfepGypnyjIg4GR6H1pDQanUNevrcsi3aQr4VzeD4szuJA/DBxH170zojcCTdCh5M7JjnETniKAXsdM27h4jbSpQIsKqLJjaBwQCBPtTOj04tolsbiFESxk49SaHrNUiMqkne87QB+LaJI9AfnUum6s3baXDbe0WUHZcADLPYgEwaO62RJWNxSHS7jiyDdDIwLA+IykwGIDEr5ciD7TRXFxWkS4ZhIJA8MRkjEtnsT3qeqsJdRrbjcpwwn/ShSVxmgFYORz6e0d6zUX1RGdzCqpZj6ACTxW7NoIoVRAUAAegFavXIjylpIGIxJ5yeBUAr0ZbXhK9kk27n9QEznfmc5Hy7VYVoCt0eymVlZWUBzn7cdSF0Cp3e8gj12gt/Arh3iV0X7durB9TZ0wOLKF2/6n4/RR/7q5eG7zXpeM+EEc+RWx7TW2dgqgszGFUd54HzpHrVkrhhDKSpB7eo/Wj2tTHEAyCGHIj0P++BSfUL27EySZNZ55J42iY1UkIVtWIMij3mXaFHIpevMkuL0zrTtFqt/cJGKX1I8p/X50DTXIxTF+6CoEQQCCZJ3SZGDgRxiuznzhs0NcZHUfsIs6i/q31Nx3dLNk2Zdi2WKkKJPACkx7iu7g18vaP7Qr2l6emi0a+AwLNdvggs7Ek+XHlxAnnGIrrP2dff7F1LOq1H3m1f0o1FtzM2yGUMpJyZDiCT27VyS3s2Hv9XrLdoBrjqgLKoLGJZjCj5kwAKZpa5dtlhbYqWI3BTzCkZj2JGaZrAplV+ivA3LyeMLjKykpibQZZUGM5yRNG0ou7n3lSu7ybeQsd/eZqN/RBizbmUshQ7TH/5cfiHY9qpCfjSCVBaG2kDHeDzHHP0xTDGq3oJueCouoyMsrDsGYhSQrErjzCD65zmpOq3Lylbxm1O62rCDuGNw5+VKVjdBdTpmZkK3CgUncoAhhGAfSDmiaS2yoFZy7AZYgCfoMClOk6y45urcCBrb7fI04gETPBgg/Wpix4b3Lpe44faNmSF7YA+cmj+CL5HFQAmABOSfU/zUBO45G2BiMgyZzPHGIqOu1a2l3tMSBgE8mOBQen9NtWmu3EUq1599ySTLABZgmBgDihRjRapLqB7bBlMwRwYMH9wayjgVlQoHUWQ6sjTDAgwYMH3HFJ6/W2tOttX3RcdbKwGaWbiYmBg+Y4/WhdTDJ4jt4l624VPBtqCRJgtMgxmTnAFWF9tqyFLRHlESf1MVSErFlUUKohVAAHoBSGn+8DUXTca393hBZCzu3Z3lvrAEenarPNK3b5QAuJlto2AmJJgn0xye1AzJu+Nwvhbec7t0/pEUSzd3LuhhzgiDgkcfTFVC6a3qrljVpdvAWt4CKzKlycHep5iJH7zxV7FH8BC2j1Iu21uBWXcAwV1KsJ7FTlT7GpanVLbTfcO0CJPzMD96A9y946KEBslGLtuEq8rtAHJBG6j3b9udjMoY8KSJP057GgCqwPfiqvqHTrpuWmsXRZUXN95QgPjCAInsff2qxR1kqCJABI754mtXC/lgA580mIEHjGcx6VEAgYSR6VOkekvfNoHUIiXZMi2xZYk7YJAPEUa0Xlt4WJ8sEyRHf3meKAYrKiRPqKlQplV/Xeq2tLp7moumEtqWPv6Ae5MAfOrAmvnX7Zfjsay79007Tp7TeZgcXbg/uq9vU57CgPBda6td1V+7qLhO665YieJ4A9gIH0pCsq8+F/hPV6+5s01ssAYa4cInzb19hJq2zPRRV6HoHwT1DWANp9M5Q/8AqNCL9GaJ+k13H4N+ybR6SLl8DU3xmXHkU/5UOPqZPyrogEYqGNnziPsV6rEzp/l4rT/8Iqg679nvUtKC13SsUHL2ouD/ANuR9RX1dWUFnxQDW6+kPtC+y6xrVa7p1WzquZAhbp9HA7n/ABDPrNfOmv0dy072rqFLiEqytypH++aFs9f8J/ZxrtXftLcsXbNhoZ7jrtGzvE8sRgfOa+gepdCU3bNxbrWggS3tUfiVXBQA8rmQexDEHsQ30DWnUaXTXlMC5bRyCOQU49s9/anrNtgWLPuBMqIA2iBj3zJ+tDFi2n6lbe/csBXD2lUklCFIfja3B4Mj2qxrIoD2JdX3MNoYbQfKZjJHciMHtJ9aAk5aRABE5zwI+Wag2oAfYQR5Z3R5eYif8XePSjk1UavWaZ9QmkuEG9t8ZUg8KY3SMAgxie9VEZZ2kgck5Jz7mY+Q4pH7has3Ll+1YBu3igussAsFwCZMeUE0W663GNpbpV7ZRmCRMTIBkHDAQfY9qd7etAanEjP80h0fWXLlpXvWTYckg22YMRBIGVxkZ+tPDA4j+KT0wV38ZbpYbdu0NKSDk/8AV2mlaFges9SexbuXBYe7t27VteZnkwcYiOec1YrcEDtI4OP70K8oRXdU3NBYhYlyBgZMSYAzSmjtjULZv3bLWrijcEY5tkiCDGCaaBrqPTHuXFcX71sKrLstkQSSDuMjkRA+ZrdPXy4jaAfWTH8GspYDGqrqfUXt3bVpbNx/F3DxFA2WoEguZkAn0q2qs64vlTJH9VOCR39jUSthukHfepthV3AkhjujaIJmI82YEe81PUahEUuzBVxk8ZwP4rNYs22B7qf7UHpllVsooHlCxHP96qRPdDcxH8Utd3oXeS67RttgCZEzB7k4wadrRqFoHMgYOf2pO/0qw11brWlN1QAtyMgAyBPMSSY9zT9bpdFaKbQpo2v3blk2zedQLrIckLKgEg9sj2p3QXiy5R0glQH5IGAecz71vQaG1ZG21bVAzMxCgCWJknHcmm6pKAqkMTnPuYEeg7UasrKhSt6dp763LzXbwuI7g2l2geGsQVkfizmacv31RS7sFVRJZjAA9STxRq5L9vYlNHbJbY73dyhiA20LtkA5igPP/ah9qvjhtJoWItHFy8JBf1VO4X1bv2xzy/pPSdRqW2aezcutxCKSB8zwPqa7v8B/AfTWQO+lR2gfjLMP0ZiP2rpml0yW1CW0VFHCqAAPoKFOK/Bn2KM0XeottHPgWzk/9bjj5L+tdn6foLVi2tqzbW2iiAqiAP0pqsoQysrKygMrKysoDK811b4H6fqdQNTf063LoAXMwY43LMMR716WsoCFtAAAAABgAdhUzWVlALauyXXaHa2ZU7kicEEjzAiDEHEwTEHNM1X9Z0yuqBhI8RTyRmfY1YU9EvZlR2iZjPE1KsoUEVwSAFYjn+01XdE0d9LanU3EuaiCrOilVK7iVAWfQ1ZXLYaVIkEQQe9TFLIB1eoW2jO5hVBJxOB8qXt6cCyV0+23KkoduASJBjHczFPmsq2PYjZ327KeKTduKqhmRY3NgEhRMCcxmBTIDbjxtgRzM5n2jj96NWVCmVlZWUB//9k="));
            }
        });


    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
//            profilePic.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
       final String randomKey = UUID.randomUUID().toString();
       StorageReference riverRef = storageReference.child("images/" + randomKey);
       riverRef.putFile(imageUri)
               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       displayURL.setText(riverRef.getDownloadUrl().toString());
                       profilePic.setImageURI(Uri.parse(riverRef.getDownloadUrl().toString()));
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(Img.this, "Gagal Upload", Toast.LENGTH_SHORT).show();
                   }
               });
    }
}