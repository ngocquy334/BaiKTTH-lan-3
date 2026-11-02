package vn.edu.ute.datphong.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ngocquy_2415053122235.R
import vn.edu.ute.datphong.helper.DatPhongHelper
import vn.edu.ute.datphong.helper.dinhDangTien
import vn.edu.ute.datphong.model.DonDatPhong

class XacNhanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xac_nhan)

        val don = intent.getSerializableExtra("don_dat_phong") as? DonDatPhong
        val tongTien = intent.getLongExtra("tong_tien", 0L)

        val txtHoTen = findViewById<TextView>(R.id.txtHoTen)
        val txtSdt = findViewById<TextView>(R.id.txtSdt)
        val txtEmail = findViewById<TextView>(R.id.txtEmail)
        val txtLoaiPhong = findViewById<TextView>(R.id.txtLoaiPhong)
        val txtNgayNhan = findViewById<TextView>(R.id.txtNgayNhan)
        val txtSoDem = findViewById<TextView>(R.id.txtSoDem)
        val txtSoKhach = findViewById<TextView>(R.id.txtSoKhach)
        val txtTongTien = findViewById<TextView>(R.id.txtTongTien)
        val btnSua = findViewById<Button>(R.id.btnSua)
        val btnXacNhan = findViewById<Button>(R.id.btnXacNhan)

        if (don != null) {
            val loai = DatPhongHelper.timLoaiPhong(don.maLoaiPhong)
            val tenLoai = loai?.ten ?: don.maLoaiPhong

            txtHoTen.text = "Họ tên: ${don.hoten}"
            txtSdt.text = "Số điện thoại: ${don.sdt}"
            txtEmail.text = "Email: ${don.email}"
            txtLoaiPhong.text = "Loại phòng: $tenLoai"
            txtNgayNhan.text = "Ngày nhận phòng: ${don.ngayNhan}"
            txtSoDem.text = "Số đêm ở: ${don.soDem}"
            txtSoKhach.text = "Số lượng khách: ${don.soKhach}"
            txtTongTien.text = "Tổng tiền: ${tongTien.dinhDangTien()}"
        }

        btnSua.setOnClickListener {
            finish()
        }

        btnXacNhan.setOnClickListener {
            if (don != null) {
                val maDon = DatPhongHelper.taoMaDon(don)
                val resultIntent = Intent()
                resultIntent.putExtra("ma_don", maDon)
                setResult(Activity.RESULT_OK, resultIntent)
            }
            finish()
        }
    }
}