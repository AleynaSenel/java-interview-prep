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
