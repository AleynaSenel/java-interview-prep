#  Java Çalışma Mekanizması ve Bellek Yönetimi

Bir Java programı yazdığında, bilgisayarın donanımı (işlemcin) bu kodu doğrudan anlamaz. Peki koddan donanıma giden bu süreç nasıl işler?



## 1. Derleme Aşaması (Compilation) ve Ara Dil

* **Kaynak Kod (`.java`):** Geliştiricinin yazdığı, belirli bir sözdizimine (syntax) sahip Java kodudur.
* **Java Derleyicisi (`javac`):** Bu derleyici, kaynak kodu doğrudan spesifik bir işletim sisteminin makine diline (0 ve 1'ler) çevirmez. Bunun yerine, kodları platformdan bağımsız bir ara formata, yani **Bytecode**'a dönüştürür ve `.class` uzantılı dosyalar oluşturur.

---

## 2. Çalıştırma Aşaması (Runtime) ve JVM

**JVM (Java Virtual Machine):** Platformdan bağımsız olan Bytecode'u okuyan ve üzerinde bulunduğu donanımın/işletim sisteminin (Windows, Linux, macOS) anlayabileceği yerel makine koduna (Native Machine Code) çevirerek çalıştıran soyut bir bilgisayardır (sanal makine).

> 💡 **"Write Once, Run Anywhere" (WORA):**  Java'nın "Bir kere yaz, her yerde çalıştır" prensibi JVM sayesinde gerçekleşir. Kod bir kez Bytecode'a derlenir ve uygun JVM'in kurulu olduğu her sistemde hiçbir değişiklik yapılmadan çalıştırılabilir.



### JVM'in Temel Alt Bileşenleri
JVM, bir programı çalıştırırken arka planda işleri spesifik alt sistemlere böler:

1. **Class Loader (Sınıf Yükleyici):** Programın çalışma zamanında (runtime) ihtiyaç duyduğu sınıf dosyalarını (`.class` / Bytecode) diskten veya ağdan bularak dinamik bir şekilde JVM'in belleğine (RAM) yükleyen, bağlayan (linking) ve başlatan (initialization) alt sistemdir. Tüm sınıflar aynı anda değil, **sadece ihtiyaç duyulduklarında** yüklenir.

2. **Bytecode Verifier (Bytecode Doğrulayıcı):** Class Loader tarafından belleğe alınan Bytecode'un Java dil standartlarına uygun olup olmadığını, sistemde yasa dışı veri tipleri dönüştürmesi (illegal type casting) veya bellek ihlali yapıp yapmayacağını kontrol eder. Güvenlik açıklarını ve sistem çökmelerini önler.
   
3. **JIT Compiler (Just-In-Time Compiler):** JVM normalde kodları satır satır yorumlayarak (Interpreter) çalıştırır, ancak bu işlem performansı düşürür. JIT Compiler, çalışma zamanında devreye girip **çok sık kullanılan kod parçalarını (hotspots)** anında yerel makine koduna derleyerek önbelleğe (cache) alır. Böylece bu kodlar tekrar çağrıldığında satır satır okunmak yerine doğrudan önbellekteki makine kodu çalıştırılır ve hız büyük ölçüde artırılır.

 **MÜLAKAT SORUSU:** *"Java derlenen (compiled) bir dil mi, yoksa yorumlanan (interpreted) bir dil mi?"*
 
 **Cevap:** Her ikisi de. Java kodu önce `javac` ile Bytecode'a derlenir. Çalışma zamanında ise JVM bu Bytecode'u satır satır yorumlar. Ancak performansı artırmak için JIT Compiler devreye girer; çok sık çalışan kod bloklarını (hotspots) tespit edip doğrudan makine koduna derler, böylece hız kazandırır.

---

## 3. Heap vs. Stack (Bellek Yönetimi)

Java'da bir program çalıştığında, veriler RAM'de iki ana bölgede tutulur.



| Özellik | Stack (Yığın) Bellek | Heap (Öbek) Bellek |
| :--- | :--- | :--- |
| **Ne Tutar?** | Metotların geçici değişkenlerini (local variables), ilkel tipleri (`int`, `double`, `boolean` vs.) ve Heap'teki nesneleri gösteren referansları (adresleri). | `new` anahtar kelimesiyle oluşturulan tüm nesneleri (objects). Örn: `new String()`, `new ArrayList()`, özel sınıflar. |
| **Nasıl Çalışır?** | **LIFO (Son giren ilk çıkar)** mantığıyla çalışır. Metot çağrıldığında yer (frame) açılır, metot bitince otomatik silinir. | Dinamik bir yapısı vardır. Metot bitse bile buradaki nesneler anında silinmez. |
| **Hız** | Çok hızlıdır. | Stack'e göre daha yavaştır (boş yer arama ve tahsis etme işlemi yapıldığı için). |
| **Hata Türü** | Sonsuz kere çağrılan (recursive) metotlarda Stack dolar ve **`StackOverflowError`** hatası alınır. | Heap tamamen dolar ve yeni nesne için yer kalmazsa **`OutOfMemoryError`** hatası alınır. |

---

## 4. Garbage Collection (GC) Nedir?

C veya C++ gibi dillerde yazılımcı, bellekte yer ayırdığı nesneyi işi bitince manuel olarak silmek zorundadır. Java'da ise bu işi arka planda otomatik olarak yapan mekanizmaya **Garbage Collector (GC)** denir. GC, Heap belleği tarar ve artık kullanılmayan *(referansı kalmamış)* nesneleri bularak bellekten siler.

Peki GC bir nesnenin silinip silinmeyeceğine nasıl karar veriyor?

### GC Roots ve Ulaşılabilirlik (Reachability)
GC, Heap'teki nesnelerin hala kullanılıp kullanılmadığını anlamak için **GC Roots (Kökler)** denilen başlangıç noktalarına bakar.

* **GC Roots Nelerdir?** Stack'te çalışan aktif lokal değişkenler, statik (static) değişkenler ve aktif thread'lerdir.
* Eğer Heap'teki bir nesneye, GC Roots üzerinden herhangi bir bağlantı (referans) varsa, o nesne **"Canlı" (Alive)** kabul edilir ve silinmez. Hiçbir bağlantısı kalmamışsa **"Ölü" (Dead)** kabul edilir ve silinir.



### Mark & Sweep (İşaretle ve Süpür) Algoritması
GC'nin temel çalışma mantığı iki adımdan oluşur:
1. **Mark (İşaretle):** GC Roots'tan başlayarak bellekteki tüm nesneleri gezer ve ulaşabildiği (referansı olan) tüm nesneleri "canlı" olarak işaretler.
2. **Sweep (Süpür):** İşaretlenmemiş (ulaşılamayan) nesneleri tespit eder ve bellekte kapladıkları alanı temizleyerek yeni nesneler için yer açar.

### G1 GC (Garbage-First)
Java 9'dan itibaren varsayılan (default) çöp toplayıcıdır.

* **Özelliği:** Heap belleği tek bir büyük alan olarak değil, küçük "bölge"lere (regions) ayırır. Temizlik yaparken, içinde en çok "ölü" nesne (çöp) bulunan bölgeyi tespit eder ve temizliğe **önce o bölgeden (Garbage-First)** başlar. Bu sayede uygulamanın anlık olarak duraksama süreleri (pause times) çok aza iner.

⚠️ **KRİTİK KAVRAM: Memory Leak (Bellek Sızıntısı)**
 
 Eğer bir nesneye teknik olarak hala bir referans varsa (GC Roots ona ulaşabiliyorsa), ama iş mantığı olarak o nesneye artık hiç ihtiyacın yoksa, GC o nesneyi **silemez.**
 
 * **En yaygın senaryo:** İçine sürekli veri eklenen ama hiç temizlenmeyen `static` bir `List` veya `HashMap`. Statik değişkenler GC Roots kabul edildiği için program çalıştığı sürece bellekte kalırlar. İşin bitse bile veriler silinmez ve en sonunda Heap dolarak `OutOfMemoryError` verir.
