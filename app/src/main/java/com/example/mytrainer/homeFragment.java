package com.example.mytrainer;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
public class homeFragment extends Fragment {

    private TextView centerBoxTextView;
    private SharedViewModel sharedViewModel;
    public homeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView tvUpper = view.findViewById(R.id.tvUpper);
        TextView tvLower = view.findViewById(R.id.tvLower);
        ImageView upperArrow = view.findViewById(R.id.upperArrow);
        ImageView lowerArrow = view.findViewById(R.id.lowerArrow);
        TextView tvWeight = view.findViewById(R.id.tv_weight);
        TextView tvMuscle = view.findViewById(R.id.tv_muscle);
        TextView tvFat = view.findViewById(R.id.tv_fat);
        TextView pBar_Upper = view.findViewById(R.id.pBar_Upper);
        ProgressBar progressBar_upper = view.findViewById(R.id.progressbar_upper); // ProgressBar 찾기
        TextView pBar_Lower = view.findViewById(R.id.pBar_Lower);
        ProgressBar progressBar_lower = view.findViewById(R.id.progressbar_lower);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getUpperBodyCount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s)
            {
                tvUpper.setText("Upper Count:"+s+"회");

                int upperCount = Integer.parseInt(s);
                progressBar_upper.setProgress(upperCount); // 진행 상태 업데이트

                if (upperCount < 30) {
                    // 예: Upper Count가 30보다 작으면 빨간색으로 설정
                    progressBar_upper.setProgressTintList(ColorStateList.valueOf(Color.RED));
                } else {
                    // 예: Upper Count가 30 이상이면 녹색으로 설정
                    progressBar_upper.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                }
                pBar_Upper.setText("Upper:"+s+"/100");
            }
        });

        sharedViewModel.getLowerBodyCount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s)
            {
                tvLower.setText("Lower Count:"+s+"회");

                int lowerCount = Integer.parseInt(s);
                progressBar_lower.setProgress(lowerCount);

                if(lowerCount < 30){
                    progressBar_lower.setProgressTintList(ColorStateList.valueOf(Color.RED));
                }
                else{
                    progressBar_lower.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                }
                pBar_Lower.setText("Lower:"+s+"/100");
            }
        });
        sharedViewModel.getWeight().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float weight) {
                tvWeight.setText("몸무게: "+weight + " kg");
            }
        });

        sharedViewModel.getMuscle().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float muscle) {
                tvMuscle.setText("골격근량: "+muscle + " kg");
            }
        });

        sharedViewModel.getFat().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float fat) {
                tvFat.setText("지방: "+fat + " kg");
            }
        });
        // 화살표를 클릭하면 프래그먼트 이동
        upperArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // exeUpperFragment로 넘어가는 로직
                navigateToFragment(new exeUpperFragment());
            }
        });
        // 화살표를 클릭하면 프래그먼트 이동
        lowerArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // exeUpperFragment로 넘어가는 로직, 하체를 위한 프래그먼트가 있다면 여기서 변경
                navigateToFragment(new exeLowerFragment());
            }
        });

        return view;
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}