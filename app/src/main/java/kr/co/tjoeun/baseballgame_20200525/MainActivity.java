package kr.co.tjoeun.baseballgame_20200525;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjoeun.baseballgame_20200525.adapters.MessageAdapter;
import kr.co.tjoeun.baseballgame_20200525.databinding.ActivityMainBinding;
import kr.co.tjoeun.baseballgame_20200525.datas.Message;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

//    문제로 사용될 3자리 숫자 배열
    int[] questionArr = new int[3];

//    채팅 내역으로 사용할 ArrayList
    List<Message> messages = new ArrayList<>();

//    Adapter를 변수로 만들고 실제 활용 => onCreate 이후에 객체화.
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                전송버튼 누르면 => 타이핑 된 값을 받아오기
                String inputValue = binding.numEdt.getText().toString();
//                 새로운 메세지로 등록
                messages.add(new Message(inputValue,"Me"));
//                리스트뷰의 내용물 변화 발생
                messageAdapter.notifyDataSetChanged();
//                리스트뷰를 맨 밑으로 끌어내려주자.
                binding.messageListView.smoothScrollToPosition(messages.size()-1);
            }
        });

    }

    @Override
    public void setValues() {

        messageAdapter = new MessageAdapter(mContext, R.layout.message_list_item, messages);
        binding.messageListView.setAdapter(messageAdapter);


        makeQuestion();
    }

//    문제로 나올 3자리 숫자를 입력
    void makeQuestion() {
//        세 자리 숫자를 채우기 위한 for
        for (int i=0 ; i < questionArr.length ; i++) {
//            각 자리에 올바른 숫자가 들어갈때까지 무한 반복
            while (true) {

//                1 <= (int) (Math.random * 9 + 1) < 10  => 1~9의 정수
                int randomNum = (int) (Math.random() * 9 + 1);

                boolean isDuplOk = true;

//                중복 검사 로직 (같은 숫자가 하나라도 있는지 조회)
                for (int num : questionArr) {
//                    문제에서 같은 숫자를 찾았다 => 중복검사 통과 X
                    if (num == randomNum) {
                        isDuplOk = false;
                        break;
                    }
                }

//                중복검사 통과?
                if (isDuplOk) {
//                    배열에 문제로 이 숫자를 채택.
                    questionArr[i] = randomNum;
//                    올바른 숫자를 뽑았으니 무한반복 종료 => 다음 숫자 뽑으러 이동.
                    break;
                }

            }
        }

        for (int num : questionArr) {
            Log.d("문제숫자", num+"");
        }

//        컴퓨터가 사람에게 환영 메세지.

        messages.add(new Message("숫자 야구게임에 오신것을 환영합니다.", "Cpu"));
        messages.add(new Message("세자리 숫자를 맞춰주세요.", "Cpu"));
        messages.add(new Message("1~9만 출제되며, 중복된 숫자는 없습니다.", "Cpu"));

//        어댑터가 사용하는 List의 내용 변경(메세지 추가)이 생겼으니 새로고침.
        messageAdapter.notifyDataSetChanged();

    }

}
