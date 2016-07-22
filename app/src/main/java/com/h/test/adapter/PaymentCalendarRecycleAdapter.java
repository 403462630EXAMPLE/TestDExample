package cn.hdmoney.hdy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wt.calendarcard.CalendarCardView;
import com.wt.calendarcard.CardPagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class PaymentCalendarRecycleAdapter extends RecyclerView.Adapter {

    private final int TYPE_CALENDAR = 1;
    private final int TYPE_EMPTY = 2;
    private final int TYPE_CONTENT = 3;
    private CardPagerAdapter.CalendarDay calendarDay;
    private List data;
    private List<CardPagerAdapter.CalendarDay> signDays;

    private CalendarCardView.OnDaySelectedListener onDaySelectedListener;

    public void setSignDays(List<CardPagerAdapter.CalendarDay> signDays) {
        this.signDays = signDays;
        notifyDataSetChanged();
    }

    public void setOnDaySelectedListener(CalendarCardView.OnDaySelectedListener onDaySelectedListener) {
        this.onDaySelectedListener = onDaySelectedListener;
    }

    public void setDatas(List data) {
        if (this.data == data || (this.data != null && data != null && this.data.size() == data.size())) {
            this.data = data;
            notifyItemRangeChanged(1, getItemCount() - 1);
        } else {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public PaymentCalendarRecycleAdapter() {
        this.calendarDay = new CardPagerAdapter.CalendarDay();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_CALENDAR;
        } else if (isEmpty()) {
            return TYPE_EMPTY;
        } else {
            return TYPE_CONTENT;
        }
    }

    private boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CALENDAR) {
            return new CalendarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_calendar, parent, false));
        } else if (viewType == TYPE_EMPTY) {
            return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_calendar_empty, parent, false));
        } else {
            return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_calendar_content, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CalendarViewHolder) {
            bindCalendarViewHolder((CalendarViewHolder) holder, position);
        } else if (holder instanceof EmptyViewHolder) {
            bindEmptyViewHolder((EmptyViewHolder) holder, position);
        } else if (holder instanceof ContentViewHolder) {
            bindContentViewHolder((ContentViewHolder) holder, position);
        }
    }

    private void bindCalendarViewHolder(CalendarViewHolder holder, int position) {
        holder.calendarCardView.setSelectedCalendarDay(calendarDay);
        holder.calendarCardView.setSignDays(signDays);
        holder.calendarCardView.setOnDaySelectedListener(new CalendarCardView.OnDaySelectedListener() {
            @Override
            public void onSelectedDay(CardPagerAdapter.CalendarDay calendarDay) {
                chanedDate(calendarDay);
            }
        });
        holder.tvNumber.setText("0");
        holder.tvTotalMoney.setText("0.00");
    }

    private void bindEmptyViewHolder(EmptyViewHolder holder, int position) {
        holder.tvDate.setText(calendarDay.year + "." + (calendarDay.month + 1) + "." + calendarDay.day);
    }

    private void bindContentViewHolder(ContentViewHolder holder, int position) {
        holder.tvName.setText("稳利银22期");
        holder.tvDate.setText(calendarDay.year + "." + (calendarDay.month + 1) + "." + calendarDay.day);
        holder.tvCost.setText("￥5000.00");
        holder.tvProfit.setText("￥300.00");
    }

    private void chanedDate(CardPagerAdapter.CalendarDay calendarDay) {
        this.calendarDay = calendarDay;
//        notifyItemRangeChanged(1, getItemCount() - 1);
        if (onDaySelectedListener != null) {
            onDaySelectedListener.onSelectedDay(calendarDay);
        }
    }

    @Override
    public int getItemCount() {
        return (isEmpty() ? 1 : data.size()) + 1;
    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.calendar_card_view)
        CalendarCardView calendarCardView;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_total_money)
        TextView tvTotalMoney;

        public CalendarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date)
        TextView tvDate;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_cost)
        TextView tvCost;
        @BindView(R.id.tv_profit)
        TextView tvProfit;
        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
