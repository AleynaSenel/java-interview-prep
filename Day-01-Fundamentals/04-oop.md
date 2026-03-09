# Kodun Mimari Yapısı (OOP)

OOP'nin 4 temel direği vardır ve mülakatlarda genellikle "Bunları bana teknik olarak açıkla ve nerede kullandığını söyle" derler.

### 1.Encapsulation (Kapsülleme): 

Sınıf içindeki verileri (değişkenleri) dış dünyadan gizlemek ve sadece senin izin verdiğin şekilde erişilmesini sağlamaktır.

**Teknik Uygulama:** Değişkenleri `private` yaparsın, onlara erişimi `public getter` ve `getter` metotları ile sağlarsın.


```java
public class Kasa {
    // 1. Veriyi dış dünyaya kapatıyoruz (private)
    private int bakiye = 5000; 

    public int getBakiye() {
        return bakiye;
    }

    public void setBakiye(int yeniBakiye) {
        if (yeniBakiye >= 0) { 
            this.bakiye = yeniBakiye; // Kurallara uyuyorsa değeri ata
        } else {
            System.out.println("Hata: Bakiye negatif olamaz!");
        }
    }
```

NOT: 

**Get (Getir/Oku):** `private` olarak gizlediğimiz bir değişkenin değerini dışarıdan okumak için kullanılır. Sadece değeri döndürür (return), değiştirmez.

**Set (Ayarla/Yaz):** `private` değişkenin değerini dışarıdan değiştirmek için kullanılır. Dışarıdan bir parametre alır.

**Peki Neden Kullanıyoruz?**

Eğer bir değişkeni `public` yaparsan, herkes o veriye kafasına göre değerler atayabilir. Ama `set` metodu kullanırsan, içeriye bir **"Kontrol/Kural (Validation)"** mekanizması kurabilirsin. Yukarıdaki örnek gibi.

Bu kodu kullanan bir başka sınıf şöyle çalışır:

```java
Kasa musteri = new Kasa();
musteri.setBakiye(-10); // Hata mesajı basar, veriyi bozmayı engelleriz!
musteri.setBakiye(25);  // Geçerli değer, değişkene atanır.

System.out.println(musteri.getBakkiye()); // 25 çıktısını verir.
```

### 2.Inheritance (Kalıtım):

Bir sınıfın, başka bir sınıfın özelliklerini ve metotlarını miras almasıdır. Kod tekrarını (Don't Repeat Yourself - DRY prensibi) önler.

**Teknik Uygulama:** `extends` anahtar kelimesi ile yapılır. (Staj Mülakatı Notu: Java'da bir sınıf aynı anda birden fazla sınıftan miras alamaz - Multiple inheritance yoktur).

```java
// Üst Sınıf (Ata)
public class Telefon {
    public void aramaYap() {
        System.out.println("Arama yapılıyor...");
    }
}

// Alt Sınıf (extends ile Telefon'un tüm özelliklerini aldı)
public class AkilliTelefon extends Telefon {
    public void interneteBaglan() {
        System.out.println("İnternete bağlanıldı.");
    }
    // AkilliTelefon artık hem aramaYap() hem interneteBaglan() kullanabilir!
}
```

Mantık: Var olan bir kodun yeteneklerini al, aynısını baştan yazma.


### 3.Polymorphism (Çok Biçimlilik): 

 Aynı isimdeki bir metodun, farklı sınıflarda farklı şekillerde çalışabilmesidir.

**Teknik Uygulama:** **Overriding** (Üst sınıftaki metodu alt sınıfta ezip kendi mantığını yazmak - `@Override`) ve **Overloading** (Aynı sınıf içinde aynı metot ismini farklı parametrelerle tekrar yazmak) ile yapılır.

```java
public class Hayvan {
    public void sesCikar() {
        System.out.println("Hayvan bir ses çıkarıyor...");
    }
}

public class Kedi extends Hayvan {
    // Üst sınıftaki metodu EZİYORUZ (Override) ve kendi yorumumuzu katıyoruz.
    @Override
    public void sesCikar() {
        System.out.println("Miyav!"); // Komut aynı ama davranış çok biçimli!
    }
}
```

**Mantık:** Aynı komutu (metodu) ver, ama her sınıf bunu kendince yapsın.

### 4.Abstraction (Soyutlama): 
İşin "nasıl" yapıldığını gizleyip, sadece "ne" yapılacağını göstermektir. Karmaşıklığı saklar. İşte mülakatların en sevdiği konu buradan doğar: Interface ve Abstract Class.

* İkisinin de ortak tek bir amacı vardır: Alt sınıflar için bir "şablon" oluşturmak. İkisinden de `new` kelimesiyle doğrudan nesne üretemezsin. Peki farkları ne?

### 1. Abstract Class (Soyut Sınıf) - "Ortak Durum ve Ortak Kod"
Eğer elindeki sınıfların ortak değişkenleri (durumları) varsa ve birebir **aynı çalışan ortak metotları varsa**, `Abstract Class` kullanırsın.

**Örnek:** 
* Bir şirkette `Mudur` ve `Isci` sınıflarımız var. İkisi de "Çalışan"dır.

* İkisinin de bir `isim` ve `maas` değişkeni olmak zorundadır.

* İkisinin de şirkete girerken kart basma kodu birebir aynıdır.

* Ama maaş hesaplama şekilleri tamamen farklıdır.

```java
  // ŞABLONUMUZ: Abstract Class
public abstract class Calisan {
    
    // 1. ÖNEMLİ FARK: Değişken (durum) tutabiliyoruz! Interface bunu yapamaz.
    protected String isim;
    protected double maas;

    // 2. ÖNEMLİ FARK: İçi dolu, çalışan ortak kod yazabiliyoruz!
    public void kartBas() {
        System.out.println(isim + " şirkete giriş yaptı.");
    }

    // 3. Sadece ismini verip "Nasıl yapılacağını alt sınıflar düşünsün" dediğimiz soyut (abstract) metot
    public abstract void maasHesapla(); 
}
 ```

Bunu miras alan (`extends`) alt sınıf şöyle görünür:


```java
public class Mudur extends Calisan {
    // kartBas() metodunu yazmama gerek yok, hazır geldi!
    // isim ve maas değişkenleri de hazır geldi!

    // Sadece soyut olanı kendime göre doldurmak ZORUNDAYIM.
    @Override
    public void maasHesapla() {
        System.out.println("Müdür maaşı = Temel Maaş + Yönetim Primi");
    }
}
```

### 2.Interface (Arayüz) - "Yetenek Sözleşmesi"
Interface'ler değişken (durum) **tutamazlar**. İçlerine normal, çalışan kod (istisnalar hariç) yazamazsın. Interface sadece bir sözleşmedir: "Eğer bu sözleşmeyi imzalarsan (`implements`), içindeki metotları kendi sınıfında tek tek yazmak zorundasın" der. Sınıflara yetenek kazandırmak için kullanılır.

Örnek: Sistemimizde bir Rapor sınıfı ve bir `Fatura` sınıfı var. Bunların hiçbir ortak değişkeni yok. Ama ikisinin de "Yazdırılabilir" olma yeteneğine ihtiyacı var.

```java
// SÖZLEŞMEMİZ: Interface
public interface Yazdirilabilir {
    
    // Değişken tutamayız! (Buraya yazacağın her şey otomatik olarak "sabit/final" olur)
    
    // Sadece metodun imzasını (adını) yazarız, gövdesi {} yoktur.
    void pdfOlarakYazdir();
    void excelOlarakYazdir();
}
```
Bunu imzalayan (`implements`) sınıf şöyle görünür:

```java
public class Fatura implements Yazdirilabilir {

    // Sözleşmeyi imzaladığım için bu iki metodu da KENDİM yazmak zorundayım.
    // Ortak bir kod gelmedi, sadece "Bunu yapmalısın" kuralı geldi.
    @Override
    public void pdfOlarakYazdir() {
        System.out.println("Fatura PDF formatına çevriliyor...");
    }

    @Override
    public void excelOlarakYazdir() {
        System.out.println("Fatura Excel formatına çevriliyor...");
    }
}
```

**Altın Kural (Özet)**
* **İçinde değişken (isim, tcNo, bakiye vb.) tutmam gerekiyor mu?** Cevap evet ise -> **Abstract Class.** (Çünkü Interface değişken tutamaz).

* **Alt sınıflara hazır, çalışan bir ortak kod (metot gövdesi) verecek miyim?** Cevap evet ise -> **Abstract Class.**

* **Sadece "Şu şu işlemleri yapmak zorundasın" diye kurallar koyup, içini tamamen alt sınıfların doldurmasını mı istiyorum?** Cevap evet ise -> **Interface.**

  4 ana maddenin etrafında dönen, bilmemiz gereken en kritik "yan detaylar" şunlardır:

### 1.Overloading (Aşırı Yükleme) vs Overriding (Ezme):

Polymorphism (Çok Biçimlilik) başlığının alt dallarıdır ve mülakatlarda "Bu ikisinin farkı nedir?" diye kesin sorulur.

**Overloading:** Aynı sınıf içinde, aynı isimde ama farklı parametreler alan metotlar yazmaktır. (Örn: topla(int a, int b) ve topla(int a, int b, int c)).

```java
public class HesapMakinesi {
    
    // 1. Metot: İki sayıyı toplar
    public int topla(int a, int b) {
        return a + b;
    }

    // 2. Metot: OVERLOADING - İsim aynı, ama üç sayı alıyor!
    public int topla(int a, int b, int c) {
        return a + b + c;
    }
}
```

**Overriding:** Kalıtımda (Inheritance), üst sınıftan gelen bir metodu alt sınıfta aynı isim ve aynı parametrelerle baştan yazıp değiştirmektir (Örn: Hayvan sınıfındaki sesCikar() metodunu Kedi sınıfında miyavlayacak şekilde değiştirmek).

```java
public class Kredi {
    public void faizHesapla() {
        System.out.println("Standart faiz: %2.0");
    }
}

public class OgrenciKredisi extends Kredi {
    // OVERRIDING - Ata sınıftaki metodu ezerek kendi kuralımızı yazdık
    @Override 
    public void faizHesapla() {
        System.out.println("Öğrenci faizi: %1.2 (İndirimli)");
    }
}
```

### 2.Erişim Belirleyiciler (Access Modifiers):

Kapsülleme (Encapsulation) yaparken sadece `private` ve `public`'ten bahsettik ama 4 tanedirler. Mülakat komitesi bunların etki alanlarını sorar:

`private:` Sadece bulunduğu sınıfın içi görebilir.

`default (hiçbir şey yazmazsan):` Sadece aynı paket (folder) içindekiler görebilir.

`protected:` Aynı paket içindekiler + miras alan (extends) alt sınıflar görebilir.

`public:` Tüm proje görebilir.


### 3.static ve final Anahtar Kelimeleri:

**`final`:** Değiştirilemez demektir. Değişkene yazarsan değeri değişmez, metoda yazarsan override edilemez, sınıfa yazarsan ondan **miras (extends) alınamaz**.

**`static`:** Sınıfa aittir, nesneye değil. Yani `new` anahtar kelimesiyle **nesne üretmeden**, direkt `SinifAdi.metotAdi()` şeklinde çağırabildiğimiz yapılardır. (Hatırla: Garbage Collector GC Roots olarak static değişkenleri baz alıyordu ve Memory Leak'e sebep olabiliyorlardı!)

```java
public class Matematik {
    
    // final: Değeri asla 3.15 yapılamaz!
    // static: Matematik.PI diyerek her yerden ulaşılabilir.
    public static final double PI = 3.14; 

    // static: new Matematik() demeden direkt Matematik.kareAl(5) yazabiliriz.
    public static int kareAl(int sayi) {
        return sayi * sayi;
    }
}
```

### 4.this ve super Kelimeleri:

**`this`:** İçinde bulunduğum bu sınıfı/nesneyi temsil eder.

**`super`:** Miras aldığım **üst (ata)** sınıfı temsil eder.

```java
public class Arac {
    int hiz = 100; // Ata sınıfın değişkeni
}

public class Araba extends Arac {
    int hiz = 200; // Kendi sınıfımın değişkeni

    public void hizlariGoster() {
        System.out.println(this.hiz);  // Çıktı: 200 (Benim hızım)
        System.out.println(super.hiz); // Çıktı: 100 (Ata sınıfın hızı)
    }
}
```























