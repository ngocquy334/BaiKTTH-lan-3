package vn.edu.ute.datphong.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import vn.edu.ute.datphong.R
import vn.edu.ute.datphong.data.DuLieuMau
import vn.edu.ute.datphong.helper.DatPhongHelper
import vn.edu.ute.datphong.helper.KiemTraHelper
import vn.edu.ute.datphong.helper.dinhDangTien
import vn.edu.ute.datphong.model.DonDatPhong
import vn.edu.ute.datphong.model.LoaiPhong

class DatPhongActivity : AppCompatActivity() {

    private lateinit var tilHoTen: TextInputLayout
    private lateinit var tilSdt: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilNgayNhan: TextInputLayout
    private lateinit var tilSoDem: TextInputLayout
    private lateinit var tilSoKhach: TextInputLayout

    private lateinit var edtHoTen: TextInputEditText
    private lateinit var edtSdt: TextInputEditText
    private lateinit var edtEmail: TextInputEditText
    private lateinit var edtNgayNhan: TextInputEditText
    private lateinit var edtSoDem: TextInputEditText
    private lateinit var edtSoKhach: TextInputEditText

    private lateinit var txtLoaiPhong: TextView
    private lateinit var txtTamTinh: TextView
    private lateinit var btnChonPhong: Button
    private lateinit var btnDatPhong: Button

    private var loaiPhongChon: LoaiPhong = DuLieuMau.dsLoaiPhong[0]

    private val chonPhongLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val maLoaiPhong = result.data?.getStringExtra("ma_loai_phong")
            if (maLoaiPhong != null) {
                val loai = DatPhongHelper.timLoaiPhong(maLoaiPhong)
                if (loai != null) {
                    loaiPhongChon = loai
                    capNhatHienThiLoaiPhong()
                    capNhatTamTinh()
                }
            }
        }
    }

    private val xacNhanLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val maDon = result.data?.getStringExtra("ma_don") ?: ""
            Snackbar.make(findViewById(android.R.id.content), "Đặt phòng thành công - Mã đơn: $maDon", Snackbar.LENGTH_LONG).show()
            xoaTrangForm()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dat_phong)

        initViews()

        if (savedInstanceState != null) {
            val maSaved = savedInstanceState.getString("KEY_MA_LOAI_PHONG")
            if (maSaved != null) {
                DatPhongHelper.timLoaiPhong(maSaved)?.let { loaiPhongChon = it }
            }
        }

        capNhatHienThiLoaiPhong()

        edtSoDem.doAfterTextChanged { capNhatTamTinh() }
        edtSoKhach.doAfterTextChanged { capNhatTamTinh() }

        btnChonPhong.setOnClickListener {
            val soKhach = edtSoKhach.text.toString().trim().toIntOrNull() ?: 1
            val intent = Intent(this, ChonPhongActivity::class.java)
            intent.putExtra("so_khach", soKhach)
            chonPhongLauncher.launch(intent)
        }

        btnDatPhong.setOnClickListener {
            xuLyDatPhong()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("KEY_MA_LOAI_PHONG", loaiPhongChon.ma)
    }

    private fun initViews() {
        tilHoTen = findViewById(R.id.tilHoTen)
        tilSdt = findViewById(R.id.tilSdt)
        tilEmail = findViewById(R.id.tilEmail)
        tilNgayNhan = findViewById(R.id.tilNgayNhan)
        tilSoDem = findViewById(R.id.tilSoDem)
        tilSoKhach = findViewById(R.id.tilSoKhach)

        edtHoTen = findViewById(R.id.edtHoTen)
        edtSdt = findViewById(R.id.edtSdt)
        edtEmail = findViewById(R.id.edtEmail)
        edtNgayNhan = findViewById(R.id.edtNgayNhan)
        edtSoDem = findViewById(R.id.edtSoDem)
        edtSoKhach = findViewById(R.id.edtSoKhach)

        txtLoaiPhong = findViewById(R.id.txtLoaiPhong)
        txtTamTinh = findViewById(R.id.txtTamTinh)
        btnChonPhong = findViewById(R.id.btnChonPhong)
        btnDatPhong = findViewById(R.id.btnDatPhong)
    }

    private fun capNhatHienThiLoaiPhong() {
        txtLoaiPhong.text = "Loại phòng: ${loaiPhongChon.ten} (${loaiPhongChon.giaMotDem.dinhDangTien()}/đêm)"
    }

    private fun capNhatTamTinh() {
        val soDem = edtSoDem.text.toString().trim().toIntOrNull() ?: 0
        val soKhach = edtSoKhach.text.toString().trim().toIntOrNull() ?: 0

        val tongTien = DatPhongHelper.tinhTienPhong_2415053122235(loaiPhongChon, soDem, soKhach)
        if (tongTien > 0) {
            txtTamTinh.text = "Tạm tính: ${tongTien.dinhDangTien()}"
        } else {
            txtTamTinh.text = "Tạm tính: 0 đ"
        }
    }

    private fun xuLyDatPhong() {
        val hoTen = edtHoTen.text.toString().trim()
        val sdt = edtSdt.text.toString().trim()
        val email = edtEmail.text.toString().trim()
        val ngayNhan = edtNgayNhan.text.toString().trim()
        val soDem = edtSoDem.text.toString().trim().toIntOrNull()
        val soKhach = edtSoKhach.text.toString().trim().toIntOrNull()

        var hopLe = true

        if (!KiemTraHelper.kiemTraHoTen(hoTen)) {
            tilHoTen.error = "Họ tên phải ít nhất 2 từ, chỉ chứa chữ cái"
            hopLe = false
        } else tilHoTen.error = null

        if (!KiemTraHelper.kiemTraSdt(sdt)) {
            tilSdt.error = "SĐT phải 10 số, bắt đầu bằng 07 hoặc 08"
            hopLe = false
        } else tilSdt.error = null

        if (!KiemTraHelper.kiemTraEmail(email)) {
            tilEmail.error = "Email kết thúc bằng @ute.udn.vn, 3-30 ký tự trước @"
            hopLe = false
        } else tilEmail.error = null

        if (!KiemTraHelper.kiemTraNgay(ngayNhan)) {
            tilNgayNhan.error = "Ngày dạng dd/MM/yyyy (năm 2026-2029)"
            hopLe = false
        } else tilNgayNhan.error = null

        if (soDem == null || soDem !in 1..15) {
            tilSoDem.error = "Số đêm phải từ 1 đến 15"
            hopLe = false
        } else tilSoDem.error = null

        if (soKhach == null || !DatPhongHelper.hopLeSoKhach(loaiPhongChon, soKhach)) {
            tilSoKhach.error = "Số khách không phù hợp với loại phòng chọn"
            hopLe = false
        } else tilSoKhach.error = null

        if (!hopLe) {
            Snackbar.make(findViewById(android.R.id.content), "Vui lòng kiểm tra lại thông tin", Snackbar.LENGTH_LONG).show()
            return
        }

        Toast.makeText(this, "Đã ghi nhận đơn đặt phòng của $hoTen", Toast.LENGTH_SHORT).show()

        val don = DonDatPhong(hoTen, sdt, email, loaiPhongChon.ma, ngayNhan, soDem!!, soKhach!!)
        val tongTien = DatPhongHelper.tinhTienPhong_2415053122235(loaiPhongChon, soDem, soKhach)

        val intent = Intent(this, XacNhanActivity::class.java)
        intent.putExtra("don_dat_phong", don)
        intent.putExtra("tong_tien", tongTien)
        xacNhanLauncher.launch(intent)
    }

    private fun xoaTrangForm() {
        edtHoTen.text?.clear()
        edtSdt.text?.clear()
        edtEmail.text?.clear()
        edtNgayNhan.text?.clear()
        edtSoDem.text?.clear()
        edtSoKhach.text?.clear()
        loaiPhongChon = DuLieuMau.dsLoaiPhong[0]
        capNhatHienThiLoaiPhong()
        txtTamTinh.text = "Tạm tính: 0 đ"
        tilHoTen.error = null
        tilSdt.error = null
        tilEmail.error = null
        tilNgayNhan.error = null
        tilSoDem.error = null
        tilSoKhach.error = null
    }
}