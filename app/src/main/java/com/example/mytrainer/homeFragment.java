package com.example.mytrainer;

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

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getUpperBodyCount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvUpper.setText("오늘 운동한 상체 횟수는 "+s+"회 입니다.");
            }
        });

        sharedViewModel.getLowerBodyCount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvLower.setText("오늘 운동한 하체 횟수는 "+s+"회 입니다.");
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