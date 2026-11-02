package vn.edu.ute.datphong.helper

object KiemTraHelper{
    fun kiemtraHoTen(hoTen: String): Boolean{
        val input = hoTen.trim()
        val regex =Regex("[a-zA]")
        return regex.matches(input)
    }
    fun kiemTraSdt(sdt: String):Boolean{
        val input = sdt.trim()
        val regex = Regex("^(07|08)\\d{8}$")
        return regex.matches(input)
    }

    fun kiemTraEmail(email: String): Boolean {
        val input = email.trim()
        val regex = Regex("^[a-zA-Z][a-zA-Z0-9._]{2,29}@ute\\.udn\\.vn$")
        return regex.matches(input)
    }

    fun kiemTraNgay(ngay: String): Boolean {
        val input = ngay.trim()
        val regex = Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(202[6-9])$")
        return regex.matches(input)
    }
}