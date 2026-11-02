package vn.edu.ute.datphong.helper
fun Long.dinhDangTien(): String{
    return String.format("%,d",this).replace(',','.')+"đ"
}