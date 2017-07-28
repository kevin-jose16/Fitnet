package com.example.olave.laboratorioandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


public class MyAdapter extends BaseExpandableListAdapter{

    private final Context _context;
    private List<String> _grupos, temp_hijos;
    private HashMap<String, List<String>> _datosGrupos;

    public MyAdapter(Context _context, List<String> _grupos, HashMap<String, List<String>> _datosGrupos) {
        this._context = _context;
        this._grupos = _grupos;
        this._datosGrupos = _datosGrupos;
    }


    @Override
    public int getGroupCount() {
        return this._grupos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._datosGrupos.get(this._grupos.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._grupos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._datosGrupos.get(this._grupos.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        ImageView image = null;//Indicador flecha desplegable derecha

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fila_grupo, null);
        }
        if (groupPosition == 0) {
            ((ImageView) convertView.findViewById(R.id.image2)).setImageResource(R.drawable.ic_gestionar);
        } else if (groupPosition == 1) {
            ((ImageView) convertView.findViewById(R.id.image2)).setImageResource(R.drawable.ic_caja);
        } else if (groupPosition == 2) {
            ((ImageView) convertView.findViewById(R.id.image2)).setImageResource(R.drawable.ic_configuracion);
        } else if (groupPosition == 3) {
            ((ImageView) convertView.findViewById(R.id.image2)).setImageResource(R.mipmap.ic_launcher);
        }

        TextView b = (TextView) convertView.findViewById(R.id.text1);
        int valor = 0;
        valor = this._datosGrupos.get(this._grupos.get(groupPosition)).size();//Asignamos el nº de elementos que hay en cada grupo
        b.setTypeface(null, Typeface.BOLD);
        b.setText(headerTitle);

        image = (ImageView) convertView.findViewById(R.id.expandableIcon);
        int imageResourceId = isExpanded ? android.R.drawable.arrow_up_float : android.R.drawable.arrow_down_float;
        image.setImageResource(imageResourceId);//Cambiamos indicador flecha desplegable a la derecha, ver fila_grupo
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fila_hijo_grupo, null);
        }

        if (childPosition == 0 && groupPosition==0 ) {
            ((ImageView) convertView.findViewById(R.id.img_hijo)).setImageResource(R.drawable.ic_socios);
        } else if (childPosition == 1 && groupPosition == 0) {
            ((ImageView) convertView.findViewById(R.id.img_hijo)).setImageResource(R.drawable.ic_panelacceso);
        } else if (childPosition == 2 && groupPosition == 0) {
            ((ImageView) convertView.findViewById(R.id.img_hijo)).setImageResource(R.drawable.ic_cobros);
        } else if (childPosition == 0 && groupPosition == 1) {
            ((ImageView) convertView.findViewById(R.id.img_hijo)).setImageResource(R.drawable.ic_movimientos);
        } else if (childPosition == 0 && groupPosition == 2) {
            ((ImageView) convertView.findViewById(R.id.img_hijo)).setImageResource(R.drawable.ic_usuarios);
        } else if (childPosition == 1 && groupPosition == 2) {
            ((ImageView) convertView.findViewById(R.id.img_hijo)).setImageResource(R.drawable.ic_actividades);
        }else if (childPosition == 2 && groupPosition == 2) {
            ((ImageView) convertView.findViewById(R.id.img_hijo)).setImageResource(R.drawable.ic_conceptos);
        }else if (childPosition == 3 && groupPosition == 2) {
            ((ImageView) convertView.findViewById(R.id.img_hijo)).setImageResource(R.drawable.ic_articulos);
        }


        TextView a = (TextView) convertView.findViewById(R.id.lblListItem);
        a.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
