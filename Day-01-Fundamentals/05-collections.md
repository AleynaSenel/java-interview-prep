# Koleksiyonlar (Collections)

Java'da birden fazla veriyi bir arada tutmak için dizileri (array) kullanırız ama dizilerin boyutu sabittir (örneğin 10 elemanlı dersen 11. elemanı ekleyemezsin). İşte Collections framework'ü bize dinamik, boyutu kendiliğinden büyüyüp küçülebilen, hazır metotlarla dolu veri yapıları sunar.

## 1. List (Listeler): ArrayList vs LinkedList

Mülakatlarda "Hangisini ne zaman kullanırsın?" diye kesin sorarlar. İkisi de sıralı veri tutar, ikisi de aynı elemandan (duplicate) birden fazla tutabilir. Ama arka plandaki çalışma mantıkları tamamen farklıdır.

### ArrayList:

Arka planda bildiğimiz dinamik bir dizi (array) kullanır.

**Avantajı:** Okuma (get) işlemi çok hızlıdır. `liste.get(500)` dediğinde anında 500. indeksi bulur.

**Dezavantajı:** Araya eleman eklemek veya silmek çok yavaştır. Çünkü araya bir eleman eklediğinde, sağındaki tüm elemanları bellekte birer adım sağa kaydırmak (shift) zorundadır.

### LinkedList:

Arka planda birbirine referanslarla bağlı düğümler (Node) gibi çalışır. Her eleman, kendinden bir öncekini ve bir sonrakini bilir (Doubly Linked List).

**Avantajı:** Araya veya başa/sona eleman eklemek ve silmek çok hızlıdır. Sadece aradaki bağları (referansları) günceller, diğer elemanları kaydırmaz.

**Dezavantajı:** Okuma işlemi yavaştır. `liste.get(500)` dersen, baştan başlayıp 1, 2, 3... diye sayarak 500. düğüme kadar tek tek gitmek zorundadır.

```java
// ArrayList (Okuma odaklı - Default tercih)
List<String> isimler = new ArrayList<>();
isimler.add("Ali");
isimler.add("Ayşe");
System.out.println(isimler.get(1)); // Çok hızlı okur: "Ayşe"

// LinkedList (Sık Ekleme/Silme odaklı)
List<String> kuyruk = new LinkedList<>();
kuyruk.add("Mehmet");
kuyruk.add(0, "Zeynep"); // Başa ekleme işlemi kaydırma yapmadığı için çok hızlıdır
```

## 2. Set (Kümeler): HashSet
Set'lerin en büyük özelliği aynı elemandan (duplicate) sadece 1 tane tutabilmesidir.

**Staj Mülakatı Tüyosu:** "Elimde içinde tekrarlı veriler (örneğin aynı TC kimlik numaraları) olan bir `List` var. Bu tekrarları en performanslı şekilde nasıl temizlersin?" derlerse cevap her zaman "Verileri bir Set'e atarım" olmalıdır.

**HashSet:** Verileri ekleme sırasına göre tutmaz. Kendi içindeki bir algoritmaya göre rastgele dizer. Sıra garantisi yoktur ama **veriyi bulma, ekleme ve silme işlemlerinde en hızlı olanıdır**.

```java
Set<String> hashSet = new HashSet<>();
hashSet.add("Elma");
hashSet.add("Armut");
hashSet.add("Elma"); // Bunu eklemez! Duplicate olduğu için görmezden gelir.

System.out.println(hashSet.size()); // Çıktı: 2
```

Not: Müfredatında TreeMap de yazıyor. O da verileri otomatik alfabetik/sayısal sıralayan bir yapıdır, ancak List veya Set değil, Map ailesindendir

## HashMap'in İç Yapısı (Under the Hood)

### 1. Map (Harita/Sözlük) Nedir?
* Şu ana kadar gördüğümüz Listelerde (ArrayList vb.) verileri sırayla alt alta ekleriz (0. indeks, 1. indeks...). Ama aradığımız veriyi bulmak için baştan sona tek tek saymamız gerekir.

* Bazen veriyi bir "sıra numarasıyla" değil, bir "etiketle" bulmak isteriz. İşte Map yapısı verileri **Anahtar-Değer (Key-Value)** çiftleri halinde tutar.

* **Gerçek Hayat Örneği:** Telefon rehberin. Ahmet (Anahtar/Key) -> 0532... (Değer/Value). Veya bir e-ticaret sitesinde T.C. Kimlik No (Key) -> Kullanıcı Bilgileri (Value).

* **Altın Kural:** Bir Map içinde Anahtarlar (Keys) tamamen benzersiz (unique) olmak zorundadır. İki tane aynı T.C. kimlik numarası olamaz. Ama Değerler (Values) aynı olabilir.

### 2. Hash Nedir?
   
* "Hash" bilgisayar biliminde bir verinin **"Dijital Parmak İzi"** demektir.

* Sen bilgisayara "Ayse" kelimesini verdiğinde, arkadaki bir matematiksel formül (Hash Fonksiyonu) bunu alır, matematiksel bir işleme sokar ve sana o kelimeye özel bir tam sayı üretir. (Örneğin: `10543`).

* **Neden böyle bir şeye ihtiyacımız var?** Çünkü bilgisayarlar milyonlarca veri arasında "Ayse" kelimesini harf harf aramak yerine, doğrudan `10543` numaralı adrese gidip bakmayı çok daha hızlı (neredeyse anında) yapar. Yani Hash, ışık hızında arama yapabilmek için kelimeleri sayılara dönüştürme taktiğidir.

### 3. HashMap Nedir?

İşte HashMap, verileri bahsettiğimiz bu **"Anahtar-Değer (Key-Value)"** mantığıyla tutan ve bunu yaparken veriyi anında bulabilmek için **"Hash (Parmak İzi)"** sistemini kullanan Java sınıfıdır.

Yüz binlerce verin olsa bile, aradığın veriyi 1 saniyeden kısa sürede bulmanı sağlar.

```java
// String(Anahtar) ve String(Değer) tutan bir HashMap oluşturuyoruz
Map<String, String> rehber = new HashMap<>();

// put() metodu ile veri EKLERİZ
rehber.put("Ahmet", "555-1234"); 
rehber.put("Ayşe", "555-8888");

// get() metodu ile Anahtar'ı verip Değer'i OKURUZ
System.out.println(rehber.get("Ahmet")); // Ekrana anında "555-1234" yazar
```












