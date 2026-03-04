# 2. String Pool ve Immutability (Değişmezlik) Tuzağı

**Püf Noktası:** String'ler değiştirilemez. String metodları (concat, toUpperCase vb.) orijinal String'i bozmaz, yeni bir String döndürür. Atama yapmazsan değişiklik havaya uçar. `==` operatörü bellekteki referansı (adresi) karşılaştırırken, `.equals()` metni karşılaştırır.

**Hap Kod:**

```java
String s1 = "Java";

String s2 = "Java";

String s3 = new String("Java");

s1.toUpperCase(); // s1 hala "Java"

System.out.println(s1 == s2); // TRUE (İkisi de String Pool'da aynı adreste)

System.out.println(s1 == s3); // FALSE (s3 'new' ile Heap'te yeni bir adres aldı)

System.out.println(s1.equals(s3)); // TRUE (İçerikleri aynı)

```

## 1. String Pool (Dizgi Havuzu) Nedir?

Java, bellekten tasarruf etmek için **Heap** içerisinde özel bir alan ayırır. Buraya **String Pool** denir.

* Bir String'i çift tırnakla ("Java") oluşturduğunda, Java önce havuza bakar: "Bende 'Java' diye bir metin var mı?"

* Varsa, yeni bir yer açmaz; mevcut olanın adresini verir.

* Yoksa, havuza ekler.

* **Amacı: Aynı metinler için tekrar tekrar bellek harcamamaktır.**

* String Pool, **Heap** bellek bölgesinin içinde yer alan özel bir bölümdür.


### 2. `new String("Java")` Ne Demek?

Bu ifade, Java'ya şu emri verir: **"Havuzda aynısı olsa bile umurumda değil, bana Heap bellekte gıcır gıcır, yepyeni ve özel bir alan (obje) oluştur!"**

* Bu yüzden `new` ile oluşturulan her String'in **adresi (referansı)** farklıdır.
  

### Mülakat sorusu: `String s = new String("Merhaba");` satırı kaç nesne oluşturur?

* **Cevap:** Eğer "Merhaba" havuzda yoksa **2 nesne** (biri havuzda, biri heap'te). Varsa sadece **1 nesne** (heap'te).

Java, `new String("Merhaba")` kodunu derlerken önce tırnak içindeki "Merhaba" literalini görür ve onu Pool'a atar (1. Nesne). Sonra `new` kelimesi çalışır ve gidip Heap'te yeni bir kopya açar (2. Nesne).

Daha da açıklamak gerekirse şöyle bir senaryo düşünelim:

```java
String s1 = "Merhaba";             // (1)
String s2 = new String("Merhaba");  // (2)
```

**Satır (1) Çalıştığında:** Java önce String Pool'a (Havuza) bakar. "`Merhaba`" yoksa onu oraya ekler. Şu an elimizde 1 nesne var (Havuzda).

**Satır (2) Çalıştığında:** Burada iki aşama var.

* Önce parantez içindeki "Merhaba" literalini görür. Havuza bakar; "Az önce eklemiştik, zaten var" der ve yeni bir tane oluşturmaz.

* Sonra `new` anahtar kelimesini görür. İşte bu "yeni" komutu, havuzdan bağımsız olarak Heap bellekte (havuzun dışında) yepyeni bir alan açar.

### Neden "Hem Havuz Hem Heap" Denir?
Eğer kodun en başında doğrudan `String x = new String("Merhaba");` yazsaydın ve daha önce hiç "Merhaba" tanımlanmamış olsaydı:

* Java "Merhaba" metnini gördüğü için onu gelecekteki verimlilik adına Havuza koyardı (1. Nesne).

* `new` dediğin için de gidip Heap'te özel bir kopya oluştururdu (2. Nesne).

**Özetle:** Çift tırnak ("") her zaman havuzu kontrol eder/doldurur, `new` ise her zaman havuzun dışında yeni bir yer açar.


## Değişmezlik (Immutability) Nedir?

Bir nesnenin (object) bellekte oluşturulduktan sonra, o nesneye ait durumun (içerdiği verinin) çalışma süresi boyunca hiçbir şekilde değiştirilememesi durumudur.

Bunu bir örnekle daha iyi anlayalım: 

Sen kodda `isim = "Ahmet";` dedikten sonra `isim = "Mehmet";` yaptığında, aslında "Ahmet" yazısını silip yerine "Mehmet" yazmıyorsun.

* Adım 1: Bellekte (Havuzda) bir "Ahmet" nesnesi oluşur. isim değişkeni bu adresi işaret eder.

* Adım 2: Sen `isim = "Mehmet";` dediğinde, "Ahmet" nesnesi orada öylece durmaya devam eder (çünkü o immutable/değişmezdir).

* Adım 3: Java gider bellekte yepyeni bir "Mehmet" nesnesi oluşturur ve senin isim değişkenini bu yeni adrese yönlendirir.

**Özet:** Değişen şey değişkenin baktığı adres, değişmeyen şey ise bellekteki verinin kendisidir.


### `concat` Metodu

`concat` metodu, aslında Java'nın o meşhur **"değişmezlik" (immutability)** kuralını en iyi kanıtlayan araçlardan biridir.

`concat` (kısaltılmış hali: concatenate), iki `String` ifadesini **uç uca ekleyerek birleştirmeye yarar.** Ancak bu işlemi yaparken arka planda şu adımları izler:

* **Orijinal Metni Korur:** Mevcut `String` nesnesine asla dokunmaz (çünkü String'ler değişmezdir).

* **Yeni Alan Açar:** Bellekte (Heap) her iki metnin birleşiminden oluşan yepyeni bir `String` nesnesi oluşturur.

* **Referans Döndürür:** Oluşturduğu bu yeni nesnenin adresini sana geri verir.


Şu meşhur örneğe bakalım:

```java
String s = "Java";
s.concat(" Kampı"); // Birleştirme yaptık güya...
System.out.println(s); // ÇIKTI: "Java"
```

Neden "Java Kampı" yazmadı? Çünkü `concat` metodu mevcut "Java" nesnesini değiştirmedi (değiştiremez!). Gitti bellekte gizlice "Java Kampı" diye üçüncü bir nesne oluşturdu ama sen onu hiçbir şeye atamadın. O yeni nesne havada asılı kaldı ve uçup gitti.


Eğer gerçekten değiştirmek istiyorsan şunu yapmalısın:
`s = s.concat(" Kampı");`
Burada bile "Java" değişmedi; `s` değişkenine, yeni oluşturulan "Java Kampı" nesnesinin adresini verdin.

**`+` Operatörü ile Farkı Nedir?**

Aslında her ikisi de birleştirme yapar ama ufak farklar vardır:

* **`+` Operatörü:** Hem `String` hem de diğer veri tiplerini (sayı vb.) birleştirebilir (Örn: "Sayı: " + 5).

* **concat Metodu:** Sadece başka bir `String` ile birleştirme yapabilir. Eğer içine `null` bir değer verirsen hata fırlatır `(NullPointerException)`, oysa `+` operatörü `null` değerini metin olarak ekler.

### Peki o sahipsiz kalan nesnelere ne oluyor?

Sahipsiz kalan o nesneler **Garbage Collector (Çöp Toplayıcı)** tarafından temizlenir.

```java
String s = "Merhaba";
s = "Dunya";
System.out.println(s);
```

**1. Unreferenced Object (Referanssız Nesne)**
Sen `s = "Dunya";` dediğin an, eski "Merhaba" nesnesi artık kimse tarafından işaret edilmediği için **"Unreferenced" (Referanssız)** durumuna düşer. Java'da bir nesneye ulaşacak hiçbir "tabela" (referans) kalmamışsa, o nesne artık bellek için bir yüktür.

**2. Garbage Collector (GC) Nasıl Çalışır?**
* Garbage Collector, JVM (Java Sanal Makinesi) içinde çalışan otomatik bir sistemdir.

* **Takip:** Sürekli belleği (Heap) tarar ve hangi nesnelerin hala kullanıldığını (bir referansı olduğunu), hangilerinin "yalnız" kaldığını kontrol eder.

* **Karar:** "Merhaba" gibi artık kimsenin ulaşamadığı bir nesne bulduğunda, onu "silinecekler" listesine ekler.

* **Temizlik:** Uygun bir zamanda (bellek dolmaya başladığında veya işlemci müsait olduğunda) bu nesneyi bellekten siler ve kapladığı alanı yeni nesneler için boşaltır.


## Değişmezliğin 3 Temel Kuralı (Mülakat Bilgisi)
Bir sınıfın immutable olması için şu şartlar gerekir:

* **Değiştirilemez İçerik:** Sınıf içindeki veriler `private` ve `final` olmalıdır.

* **Setter Metotlarının Yokluğu:**  Nesnenin içindeki veriyi değiştirecek `set...()` metotları bulunmamalıdır.

* **Yeni Nesne Dönüşü:** Eğer bir metot (örneğin `toUpperCase()`) veride değişiklik yapacaksa, orijinal veriyi bozmak yerine yepyeni bir nesne oluşturup onu döndürmelidir.


<details>
<summary><b>EKSTRA NOT(OKUMAK İSTEYENLERE):</b></summary>
 


Burada final, private kısmına daha detaylı bakalım:

**1. Final (Tabelayı Çivilemek)**

final anahtar kelimesi referans (adres) ile ilgilidir.

Bir değişkeni final yaptığında, o değişkenin tuttuğu adresi bir daha değiştiremezsin.

Örnek: `final String s = "Java";` dediğinde, `s` tabelasını artık başka bir nesneye ("Python") çeviremezsin. Ama bu, nesnenin içeriğiyle ilgili değil, sadece senin o adrese olan bağlılığınla ilgilidir.

**2. Immutability / Değişmezlik (Kasadaki Veriyi Mühürlemek)**

Değişmezlik ise nesnenin (verinin) kendisi ile ilgilidir.

Java'da `String` sınıfı öyle bir yazılmıştır ki, bellekte bir kez "Java" nesnesi oluştuysa, o nesnenin içindeki 'J', 'a', 'v', 'a' karakterlerini kimse değiştiremez.

Sen `s = "Hava";` desen bile, eski "Java" nesnesi bellekte hala "Java" olarak kalır. Sen sadece yeni bir nesne yaratıp tabelanı ona çevirirsin.

**İşler Diğerlerinde Nasıl İşliyor?**

Mesela senin bir `Ogrenci` sınıfın olsun:

Eğer bu sınıfın içinde `public String isim;` dersen, bu sınıf immutable değildir. Çünkü birisi `ogrenci.isim = "Ayse";` diyerek nesnenin içindeki veriyi değiştirebilir.

Eğer `private` `final` `String isim;` dersen ve bunu sadece Constructor (yapıcı metot) ile bir kez belirlersen, işte o zaman bu sınıfı **Immutable** yapmış olursun. Çünkü dışarıdan kimse o veriyi değiştiremez (private) ve içeriden de bir daha atama yapılamaz (final).

**Özetle:** `String` sınıfının yapımcıları, sınıfın içindeki veriyi `private` `final` yaparak bize değişmez (immutable) bir yapı sunmuşlardır. Biz değişkenimizi `final` yapmasak bile (yani tabelayı başka yere çevirebilsek bile), baktığımız nesnelerin içeriği her zaman mühürlüdür.
</details>

### 3. Neden Gereklidir?
**Thread-Safe (Güvenli Eşzamanlılık):** Aynı metne 10 farklı işlem aynı anda erişse bile, metin asla değişmeyeceği için veri bozulma riski yoktur.

**String Pool Verimliliği:** Eğer String'ler değişebilir olsaydı, havuzda aynı "Şifre123" metnine bakan binlerce kullanıcıdan biri şifresini değiştirdiğinde herkesinki değişirdi. Değişmezlik, havuz sisteminin temel taşıdır



## Lab Soruları


### Soru 1:
String Pool da aynı adresleri gösterdiğini kanıtlayalım.
```java
String bitki1 = "papatya";
bitki1 = "yasemin";
String bitki2 = "yasemin";
int s1 = System.identityHashCode(bitki1);
int s2 = System.identityHashCode(bitki2);

System.out.println( s1);
System.out.println( s2);
System.out.println(bitki1 == bitki2);
```

<details>
<summary><b>👉 Cevabı Görmek İçin Tıkla</b></summary>

**Cevap:** bitki1 ve bitki2 değişkenleri tamamen farklı zamanlarda tanımlanmış olsalar bile, tırnak içinde aynı literal değere ("yasemin") sahip oldukları için String Pool'daki tek ve ortak bir nesneyi işaret ederler.
</details>


### Soru 2: 

Aşağıdaki kod çalıştırıldığında ekrana ne yazdırır? Neden?

```java
String metin = "Java";
metin.concat(" Kampı"); 
metin.toUpperCase();
System.out.println(metin);
```

<details>
<summary><b>👉 Cevabı Görmek İçin Tıkla</b></summary>

**Cevap:** Ekrana Java yazdırır! ("JAVA" veya "Java Kampı" yazmaz!)
</details>

### Soru 3:

Aşağıdaki iki satırlık kod sonucunda Heap bellekte (String Pool dahil) toplamda kaç tane farklı String nesnesi oluşur?

```java
String str = "Hello";
str = str + " World";
```

<details>
<summary><b>👉 Cevabı Görmek İçin Tıkla</b></summary>

**Cevap:**

Hemen bellek senaryosunu (Heap/String Pool) adım adım canlandıralım:

* `String str = "Hello";`: Java, String Pool'da "Hello" nesnesini oluşturur. (1. Nesne)

* " World": Kodun sağ tarafında tırnak içinde yeni bir literal var. Java bunu da Pool'a ekler. (2. Nesne)

* `str + " World":` Java'da değişkenlerle (buradaki `str`) yapılan `String` birleştirme işlemleri (`+` veya `concat`) arka planda **StringBuilder** kullanır ve ortaya çıkan yeni `String` ("Hello World") String Pool'a atılmaz, doğrudan normal **Heap alanında** oluşturulur. (Ancak "Hello" ve " World" kelimeleri doğrudan çift tırnakla yazıldığı için Pool'a atılırlar). (3. Nesne)

Sonra sen `=` ile str tabelasını bu 3. nesneye yönlendirirsin. İlk iki nesne artık referanssız kalabilir ama bellekte bir kez oluştular!

</details>

### Soru 4:

Aşağıdaki karşılaştırmanın sonucu nedir?

```java
String a = new String("Yıldız");
String b = new String("Yıldız");
System.out.println(a.equals(b));
```

<details>
<summary><b>👉 Cevabı Görmek İçin Tıkla</b></summary>

**Cevap:** `equals()` metodu nesnelerin adreslerine bakmaz, bizzat karakter dizilimlerini (içeriği) kontrol eder. `new` ile oluşturulmuş farklı iki "Yıldız" nesnesi, farklı adreslerde olsalar da içerikleri aynı olduğu için bu testten başarıyla geçerler.
</details> 
