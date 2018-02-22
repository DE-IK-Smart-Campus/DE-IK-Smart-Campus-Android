package hu.unideb.smartcampus.listview.adapter.event;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hu.unideb.smartcampus.R;

public class EventDetailsAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> itemname;
    private final List<Integer> imgid;

    public EventDetailsAdapter(Activity context, List<String> itemname, List<Integer> imgid) {
        super(context, R.layout.event_details_list_row, itemname);

        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView = inflater.inflate(R.layout.event_details_list_row, null, true);

        ImageView imageView = rowView.findViewById(R.id.event_details_icon);
        TextView text = rowView.findViewById(R.id.event_info_text_view);

        imageView.setImageResource(imgid.get(position));
        text.setText(itemname.get(position));

        return rowView;

    }
}
