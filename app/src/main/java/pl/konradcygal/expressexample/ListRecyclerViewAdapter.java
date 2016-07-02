package pl.konradcygal.expressexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import pl.konradcygal.expressexample.databinding.ChatItemMeBinding;
import pl.konradcygal.expressexample.databinding.ChatItemSomeoneBinding;

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatItem> items;
    private LayoutInflater inflater;

    public ListRecyclerViewAdapter(Context context, ArrayList<ChatItem> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatItem item = items.get(position);
        if (getItemViewType(position) == 0) {
            ((MeViewHolder) holder).bindTo(item);
        } else if (getItemViewType(position) == 1) {
            ((SomebodyViewHolder) holder).bindTo(item);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0)
            return MeViewHolder.create(inflater, parent);
        else
            return SomebodyViewHolder.create(inflater, parent);
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MeViewHolder extends RecyclerView.ViewHolder {
        private ChatItemMeBinding binding;

        MeViewHolder(ChatItemMeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        static MeViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            ChatItemMeBinding binding =
                ChatItemMeBinding.inflate(inflater, parent, false);
            return new MeViewHolder(binding);
        }

        public void bindTo(ChatItem item) {
            binding.setMessage(item);
        }
    }

    static class SomebodyViewHolder extends RecyclerView.ViewHolder {
        private ChatItemSomeoneBinding binding;

        SomebodyViewHolder(ChatItemSomeoneBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        static SomebodyViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            ChatItemSomeoneBinding binding =
                ChatItemSomeoneBinding.inflate(inflater, parent, false);
            return new SomebodyViewHolder(binding);
        }

        public void bindTo(ChatItem item) {
            binding.setMessage(item);
        }
    }
}

