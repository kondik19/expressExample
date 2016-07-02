package pl.konradcygal.expressexample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

import pl.konradcygal.expressexample.databinding.FragmentMessagesBinding;

public class MessagesFragment extends Fragment {
    private FragmentMessagesBinding binding;
    private ListRecyclerViewAdapter adapter;
    private ArrayList<ChatItem> items;

    public static MessagesFragment newInstance() {
        MessagesFragment fragment = new MessagesFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messages, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        items = new ArrayList<>();
        items.add(new ChatItem("Hello", 0));
        items.add(new ChatItem("Hi", 1));
        items.add(new ChatItem("How are you", 0));
        items.add(new ChatItem("Great, thanks…", 1));

        adapter = new ListRecyclerViewAdapter(getContext(), items);
        binding.list.setAdapter(adapter);
        setSwipeItem();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.list.setLayoutManager(layoutManager);
        binding.list.setHasFixedSize(true);
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(binding.newMessage.getText().toString())) {
                    items.add(new ChatItem(binding.newMessage.getText().toString(), 0));
                    binding.newMessage.setText("");
                    adapter.notifyDataSetChanged();
                    binding.list.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.list.smoothScrollToPosition(items.size());
                        }
                    }, 100);
                }
            }
        });
    }

    private void setSwipeItem() {
        final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener =
            new SwipeToDismissTouchListener<>(
                new RecyclerViewAdapter(binding.list),
                new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(RecyclerViewAdapter view, int position) {
                        items.remove(position);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                    }
                });

        binding.list.setOnTouchListener(touchListener);
        binding.list.addOnScrollListener(
            (RecyclerView.OnScrollListener) touchListener.makeScrollListener()
        );
        binding.list.addOnItemTouchListener(new SwipeableItemClickListener(getContext(),
            new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (view.getId() == R.id.txt_delete) {
                        touchListener.processPendingDismisses();
                    } else if (view.getId() == R.id.txt_undo) {
                        touchListener.undoPendingDismiss();
                    }
                }
            }) {
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }

        });
    }
}
