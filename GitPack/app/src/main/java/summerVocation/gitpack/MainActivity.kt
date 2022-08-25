package summerVocation.gitpack

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import summerVocation.gitpack.databinding.ActivityMainBinding
import summerVocation.gitpack.service.checkTodayCommitService
import summerVocation.gitpack.viewmodel.calaenderViewModel

class MainActivity : AppCompatActivity() {
    lateinit var userI : String
    private lateinit var mbinding : ActivityMainBinding

    lateinit var mycalaenderViewModel: calaenderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        mycalaenderViewModel=ViewModelProvider(this).get(calaenderViewModel::class.java)


        var userId = intent.getStringExtra("loginId")

        /// 로그인한 아이디 확인 자동로그인시 if 문 사용 -> 반대 else문
        if (userId == null){
            try{
            val db: SQLiteDatabase = SQLiteDBHelper(this).readableDatabase
            val cursor = db.rawQuery("select * from tb_login",null)
            if(cursor.moveToFirst()){
                userId= cursor.getString(0)
                //binding.mainText.setText(userId)
            }
            db.close()}catch (e: Exception){

            }
        }else{
           //binding.mainText.setText(userId)

        }
        userI=userId!!
        mycalaenderViewModel.updateId(userId)



        println(userId)





        //네비게이션들을 담는 호스트
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.myNavHost) as NavHostFragment

        //네비게이션 컨트롤러 가져옴
        val navController = navHostFragment.navController
        //바텀네비게이션뷰와 네비게이션을 묶어준다
        NavigationUI.setupWithNavController(mbinding.myBottomNav , navController)





    }
    fun getuserId() : String{

        return userI
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(applicationContext,checkTodayCommitService::class.java)
        startService(intent)
        println("파괴됨")
    }

}


