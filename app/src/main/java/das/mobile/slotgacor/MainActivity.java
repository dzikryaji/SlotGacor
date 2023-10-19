package das.mobile.slotgacor;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//  Nama Anggota Kelompok : Dzikry Aji Santoso 215150400111029
//                          Odilia Ning Intan Restu Hapsari 215150401111023
//  PAPB SI - B

    private ImageView ivSlotStart, ivSlotCenter, ivSlotEnd;
    private Button btStartStop;
    private Thread thread;
    private Handler handler;
    private int[] slotImgs = {
            R.drawable.slot_1,
            R.drawable.slot_2,
            R.drawable.slot_3,
            R.drawable.slot_4,
            R.drawable.slot_5,
            R.drawable.slot_6,
            R.drawable.slot_7,
            R.drawable.slot_8,
            R.drawable.slot_9
    };
    private int slotStartPosition = 8;
    private int slotCenterPosition = 8;
    private int slotEndPosition = 8;
    private int runningSlot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btStartStop = findViewById(R.id.btStartStop);
        this.ivSlotStart = findViewById(R.id.iv_slot_start);
        this.ivSlotCenter = findViewById(R.id.iv_slot_center);
        this.ivSlotEnd = findViewById(R.id.iv_slot_end);
        this.btStartStop.setOnClickListener(this);
        this.handler = new Handler(Looper.getMainLooper());
    }

    private void createThread() {
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        switch (runningSlot) {
                            case 3:
                                if (slotStartPosition == 8) slotStartPosition = 0;
                                else slotStartPosition++;
                            case 2:
                                if (slotCenterPosition == 8) slotCenterPosition = 0;
                                else slotCenterPosition++;
                            case 1:
                                if (slotEndPosition == 8) slotEndPosition = 0;
                                else slotEndPosition++;
                                break;
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ivSlotStart.setImageResource(slotImgs[slotStartPosition]);
                                ivSlotCenter.setImageResource(slotImgs[slotCenterPosition]);
                                ivSlotEnd.setImageResource(slotImgs[slotEndPosition]);
                            }
                        });
                        Thread.sleep(200);
                    }
                } catch (Exception e) {

                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (runningSlot > 1) {
            runningSlot--;
        } else if (runningSlot == 1) {
            runningSlot--;
            thread.interrupt();
            if (slotStartPosition == slotCenterPosition && slotCenterPosition == slotEndPosition ){
                findViewById(R.id.iv_jackpot).setVisibility(View.VISIBLE);
                findViewById(R.id.iv_jackpot1).setVisibility(View.VISIBLE);
            }
            btStartStop.setText("Spin");
        } else {
            runningSlot = 3;
            btStartStop.setText("Stop");
            findViewById(R.id.iv_jackpot).setVisibility(View.INVISIBLE);
            findViewById(R.id.iv_jackpot1).setVisibility(View.INVISIBLE);
            createThread();
            this.thread.start();
        }

    }
}