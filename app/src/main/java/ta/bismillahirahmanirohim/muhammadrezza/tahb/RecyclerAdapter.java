package ta.bismillahirahmanirohim.muhammadrezza.tahb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by lenovo Z410p on 15/08/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Myhoder>{
    private static final String TAG="Recycleradapter";
    List<FireModel> list;
    Context context;

    public RecyclerAdapter(List<FireModel> list, Context context){
        this.list=list;
        this.context=context;
    }

    @Override
    public Myhoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        Myhoder myhoder = new Myhoder(view);

        return myhoder;
    }

    @Override
    public void onBindViewHolder(Myhoder holder, int position) {
        FireModel mylist=list.get(position);
        holder.date.setText(mylist.getDatee());
        holder.time.setText(mylist.getTimee());
        holder.hblevels.setText(mylist.getHblevels());

    }

    @Override
    public int getItemCount() {
        int arr=0;
        try{
            if(list.size()==0){
                arr=0;

            }else{
                arr=list.size();
            }

        }catch (Exception e){
            Log.e(TAG, "getItemCount: errior list" );
        }

        return arr;
    }

    class Myhoder extends RecyclerView.ViewHolder {
            TextView date,time,hblevels;
        public Myhoder(View itemView) {
            super(itemView);
            date= (TextView) itemView.findViewById(R.id.date);
            time= (TextView) itemView.findViewById(R.id.time);
            hblevels= (TextView) itemView.findViewById(R.id.hblevels);
        }
    }
}
