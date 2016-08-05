package com.seznam_kontaktu.seznamkontaktu.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seznam_kontaktu.seznamkontaktu.BR;
import com.seznam_kontaktu.seznamkontaktu.Model.ContactItem;
import com.seznam_kontaktu.seznamkontaktu.R;

import java.util.List;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.BindingHolder> {

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        DialogAdapter.listener = listener;
    }

    private List<ContactItem> items;
    Context mContext;

    public DialogAdapter(Context context, List<ContactItem> contactItems) {
        this.mContext = context;
        this.items = contactItems;
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHolder(final View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_dialog, parent, false);
        BindingHolder holder = new BindingHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final ContactItem contactItem = items.get(position);
        holder.getBinding().setVariable(BR.contactItem, contactItem);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
