var hesapBakiyesi: Double = 1000.0

fun main() {
    print("Lutfen kart sifrenizi girin: ")
    val girilenSifre = readln()

    if (girilenSifre != "1234") {
        println("Sifre hatali. Uygulama kapatiliyor.")
        return
    }

    println("Sisteme basariyla giris yaptiniz.")

    while (true) {
        println("\n--- BANKA ISLEMLERI ---")
        println("1- Bakiyeyi Gor")
        println("2- Para Ekle")
        println("3- Para Cikar")
        println("4- Havale / EFT")
        println("5- Kredi Talebi")
        println("0- Oturumu Kapat")
        print("Tercihinizi girin: ")

        val tercih = readln().toIntOrNull()

        when (tercih) {
            0 -> {
                println("Isleminiz sonlandirildi. Iyi gunler dileriz.")
                break
            }

            1 -> bakiyeyiGoster()

            2 -> {
                print("Eklenecek tutari yazin: ")
                val eklenecekTutar = readln().toDoubleOrNull()

                if (eklenecekTutar != null) {
                    paraEkle(eklenecekTutar)
                } else {
                    println("Hatali tutar girdiniz.")
                }
            }

            3 -> {
                print("Cekilecek tutari yazin: ")
                val cekilecekTutar = readln().toDoubleOrNull()

                if (cekilecekTutar != null) {
                    paraCikar(cekilecekTutar)
                } else {
                    println("Hatali tutar girdiniz.")
                }
            }

            4 -> {
                print("Gonderim yapilacak IBAN: ")
                val hedefIban = readln()

                print("Transfer miktari: ")
                val transferTutari = readln().toDoubleOrNull()

                if (transferTutari != null) {
                    havaleGonder(hedefIban, transferTutari)
                } else {
                    println("Gecersiz miktar girdiniz.")
                }
            }

            5 -> {
                print("Talep ettiginiz kredi miktari: ")
                val istenenKredi = readln().toDoubleOrNull()

                print("Odeme suresi (ay): ")
                val taksitSuresi = readln().toIntOrNull()

                if (istenenKredi != null && taksitSuresi != null) {
                    krediTalebiOlustur(istenenKredi, taksitSuresi)
                } else {
                    println("Kredi bilgileri hatali girildi.")
                }
            }

            else -> println("Menu disi bir secim yaptiniz.")
        }
    }
}

fun bakiyeyiGoster() {
    println("Guncel hesap bakiyeniz: $hesapBakiyesi TL")
}

fun paraEkle(tutar: Double) {
    if (tutar > 0) {
        hesapBakiyesi += tutar
        println("$tutar TL hesabiniza eklendi.")
    } else {
        println("Pozitif bir tutar girmelisiniz.")
    }
}

fun paraCikar(tutar: Double) {
    if (tutar > 0 && tutar <= hesapBakiyesi) {
        hesapBakiyesi -= tutar
        println("$tutar TL hesabinizdan cekildi.")
    } else {
        println("Islem gerceklestirilemedi. Bakiye yetersiz veya tutar hatali.")
    }
}

fun havaleGonder(ibanBilgisi: String, tutar: Double) {
    if (tutar > 0 && tutar <= hesapBakiyesi) {
        hesapBakiyesi -= tutar
        println("$ibanBilgisi hesabina $tutar TL gonderildi.")
        println("Islem sonrasi kalan bakiye: $hesapBakiyesi TL")
    } else {
        println("Transfer yapilamadi. Yetersiz bakiye veya gecersiz tutar.")
    }
}

fun krediTalebiOlustur(krediTutari: Double, aySayisi: Int) {
    if (krediTutari <= hesapBakiyesi * 10) {
        println("$aySayisi ay vadeli $krediTutari TL kredi talebiniz uygun bulundu.")
    } else {
        println("Kredi talebiniz su an icin onaylanmadi.")
    }
}