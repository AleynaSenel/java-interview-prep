public class LabSoru1 {
    public static void main(String[] args) {
        
        int sayi1 = 100;
        byte sayi2 = 50;
        
        System.out.println("Başlangıç değerleri -> sayi1: " + sayi1 + ", sayi2: " + sayi2);

        // AŞAĞIDAKİ SATIR DERLEME HATASI (Compile Error) VERİR!
        // (Hatayı görmek için başındaki '//' işaretlerini silip kodu çalıştırmayı dene)
        
        // sayi2 = sayi1 + 10; 

        
        // DOĞRU KULLANIM (Explicit Casting - Açık Tip Dönüşümü)
        // Sonucu zorla byte'a sığdırıyoruz:
        
        sayi2 = (byte) (sayi1 + 10);
        
        System.out.println("İşlem sonrası sayi2'nin yeni değeri: " + sayi2);
    }
}
