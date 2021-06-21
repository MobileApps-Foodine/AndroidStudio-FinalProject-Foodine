package com.projectakhir.foodine.MainApp.AddMenu

import android.content.Context
import android.text.*
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.projectakhir.foodine.DataClass.ShoppinglistItem
import com.projectakhir.foodine.R
import kotlinx.android.synthetic.main.activity_add_shoppinglist.*
import java.lang.Exception

class ShoppinglistItemAdapter() : BaseAdapter() {
    private lateinit var mInflater : LayoutInflater
    private lateinit var context : Context
    private var layoutResourceID: Int = 0
    lateinit var myItems : ArrayList<ShoppinglistItem>

    fun ShoppinglistItemAdapter(context: Context, myItems: ArrayList<ShoppinglistItem>, layoutResourceID:Int){
        this.context = context
        this.myItems = myItems
        this.layoutResourceID = layoutResourceID
    }

    class ViewHolder{
        lateinit var drag : ImageView
        lateinit var check : CheckBox
        lateinit var content : EditText
        lateinit var qty : EditText
        lateinit var delete : ImageView
    }

    override fun getCount(): Int {
        return myItems.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return  position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder : ViewHolder
        var convertView: View? = convertView
        if(convertView == null){
            holder = ViewHolder()
            mInflater = LayoutInflater.from(context)
            convertView = mInflater.inflate(layoutResourceID, null)
            holder.drag = convertView.findViewById(R.id.row_shoppinglist_item_drag)
            holder.check = convertView.findViewById(R.id.row_shoppinglist_item_check)
            holder.content = convertView.findViewById(R.id.row_shoppinglist_item_content)
            holder.qty = convertView.findViewById(R.id.row_shoppinglist_item_quantity)
            holder.delete = convertView.findViewById(R.id.row_shoppinglist_item_delete)
            for(each in arrayListOf(holder.drag, holder.check, holder.content, holder.qty, holder.delete)){
                each.setTag(position)
            }
            convertView.setTag(holder)
        } else {
            holder = convertView.tag as ViewHolder
        }

        for(each in arrayListOf(holder.drag, holder.check, holder.content, holder.qty, holder.delete)){
            each.id = position
        }
        val itemTxt = myItems[position].item
        val qtyTxt = if(myItems[position].quantity == null){ null }
            else{ myItems[position].quantity.toString() }
        holder.content.setText(itemTxt)
        holder.qty.setText(qtyTxt)

        //todo : drag to move
//        holder.drag.setOnClickListener {
//            Toast.makeText(context, "drag $position", Toast.LENGTH_SHORT).show()
//        }

        holder.check.setOnCheckedChangeListener { buttonView, isChecked ->
            val currentItem = holder.content.text.toString()
            val currentQty = holder.qty.text.toString()
            holder.content.setText(if(isChecked){ strikethrough(currentItem) } else{ currentItem })
            holder.qty.setText(if(isChecked){ strikethrough(currentQty) } else{ currentQty })
            myItems[holder.check.id].status = isChecked
        }

        holder.content.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try{
                    myItems[holder.content.id].item = holder.content.text.toString()
                } catch (e:Exception){ }
                if(getCount() > 1){
                    (context as AddShoppinglistActivity).showAddListItem(myItems[getCount()-1].item!!.isNotEmpty())
                }
            }
        })

        holder.qty.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try{
                    myItems[holder.qty.id].quantity = (holder.qty.text.toString()).toInt()
                } catch (e:Exception){ }
            }
        })

        holder.delete.setOnClickListener{
            if(count>1){
                myItems.removeAt(holder.delete.id)
                notifyDataSetChanged()
                (context as AddShoppinglistActivity).showAddListItem(true)
//                Toast.makeText(context, "${holder.delete.id} $myItems ${holder.content.text}", Toast.LENGTH_SHORT).show()
            } else {
                myItems.set(0, ShoppinglistItem("", null, false))
                notifyDataSetChanged()
            }
        }
        return convertView!!
    }

    private fun strikethrough(string:String?) : SpannableString? {
        if(string == null){
            return null
        }else{
            val spannable : SpannableString = SpannableString(string)
            spannable.setSpan(StrikethroughSpan(), 0, string.length, 0)
            return spannable
        }
    }
}