package com.example.olave.laboratorioandroid;

import android.annotation.TargetApi;
import android.app.ExpandableListActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainApp extends ActionBarActivity {

    private Toolbar toolbar;//Cajón de navegación para icono animado estilo Play Store
    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerExpandableList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    //private CharSequence mTitle;

    private List<String> grupos;
    private HashMap<String, List<String>> datosGrupos;
    private int ultimaPosicionExpList = -1;
    private MyAdapter adapter;
    ImageView imageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mDrawerTitle = getTitle();

        toolbar = (Toolbar) findViewById(R.id.toolbar);//Añadimos include en activity_main y estilo en styles
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerExpandableList = (ExpandableListView) findViewById(R.id.explist_slidermenu);
        mDrawerExpandableList.setGroupIndicator(null);//Indicador flecha desplegable izquierda oculta

        //A continuación añadimos cabecera general...
        View header = getLayoutInflater().inflate(R.layout.cabecera_general, null);
        mDrawerExpandableList.addHeaderView(header, null, false);
        //...y pie de página
        View footer = getLayoutInflater().inflate(R.layout.pie_pagina, null);
        mDrawerExpandableList.addFooterView(footer, null, true);


        cargarDatos();

        if (toolbar != null) {
            toolbar.setTitle(mDrawerTitle);
            //toolbar.setSubtitle(mTitle);
            //toolbar.setLogo(R.mipmap.ic_launcher);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View view) {

                getSupportActionBar().setTitle(mDrawerTitle);
                //getSupportActionBar().setSubtitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                getSupportActionBar().setTitle("");
                getSupportActionBar().setSubtitle("");
                invalidateOptionsMenu();

            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);//Mostrar icono menu animado
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerExpandableList.setTextFilterEnabled(true);
       /* mDrawerExpandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                int grup_pos = (int)adapter.getGroupId(groupPosition);
                if(grup_pos == 0) {
                    Toast.makeText(getApplicationContext(), "Grupo 1", Toast.LENGTH_SHORT).show();
                    mDrawerTitle = "Grupo 1";
                }
                if(grup_pos == 1) {
                    Toast.makeText(getApplicationContext(), "Grupo 2", Toast.LENGTH_SHORT).show();
                    mDrawerTitle = "Grupo 2";
                }
                if(grup_pos == 2) {
                    Toast.makeText(getApplicationContext(), "Grupo 3", Toast.LENGTH_SHORT).show();
                    mDrawerTitle = "Grupo 3";
                }
                if(grup_pos == 3) {
                    Toast.makeText(getApplicationContext(), "Grupo 4", Toast.LENGTH_SHORT).show();
                    mDrawerTitle = "Grupo 4";
                }
                mDrawerLayout.closeDrawer(mDrawerExpandableList);
                return false;
            }
        });*/
        mDrawerExpandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (ultimaPosicionExpList != -1 && groupPosition != ultimaPosicionExpList) {
                    mDrawerExpandableList.collapseGroup(ultimaPosicionExpList);
                }
                ultimaPosicionExpList = groupPosition;
            }
        });
        mDrawerExpandableList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });


        mDrawerExpandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int grup_pos = (int)adapter.getGroupId(groupPosition);
                int child_pos = (int)adapter.getChildId(groupPosition, childPosition);
                if(grup_pos == 0) {
                    switch (child_pos) {
                        case 0:
                            Toast.makeText(getApplicationContext(), "Socios", Toast.LENGTH_SHORT).show();
                            mDrawerTitle = "Socios";
                            //mTitle = "Hijo 1 Grupo 1";
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "Panel de Acceso", Toast.LENGTH_SHORT).show();
                            mDrawerTitle = "Hijo 2 Grupo 1";
                            //mTitle = "Hijo 2 Grupo 1";
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "Cobros", Toast.LENGTH_SHORT).show();
                            mDrawerTitle = "Cobros";
                            //mTitle = "Hijo 2 Grupo 1";
                            break;
                        default:
                            break;
                    }
                }
                if(grup_pos == 1) {
                    switch (child_pos) {
                        case 0:
                            Toast.makeText(getApplicationContext(), "Movimientos", Toast.LENGTH_SHORT).show();
                            mDrawerTitle = "Movimientos";
                            //mTitle = "Hijo 1 Grupo 2";
                            break;
                        default:
                            break;
                    }

                }
                if(grup_pos == 2) {
                    switch (child_pos) {
                        case 0:
                            Toast.makeText(getApplicationContext(), "Usuarios", Toast.LENGTH_SHORT).show();
                            mDrawerTitle = "Usuarios";
                            //mTitle = "Hijo 1 Grupo 1";
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "Actividades", Toast.LENGTH_SHORT).show();
                            mDrawerTitle = "Actividades";
                            //mTitle = "Hijo 2 Grupo 1";
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "Conceptos", Toast.LENGTH_SHORT).show();
                            mDrawerTitle = "Conceptos";
                            //mTitle = "Hijo 2 Grupo 1";
                            break;
                        case 3:
                            Toast.makeText(getApplicationContext(), "Articulos", Toast.LENGTH_SHORT).show();
                            mDrawerTitle = "Articulos";
                            //mTitle = "Hijo 2 Grupo 1";
                            break;
                        default:
                            break;
                    }
                }
                mDrawerLayout.closeDrawer(mDrawerExpandableList);
                return false;
            }
        });

        if (savedInstanceState == null) {
            displayView(0);
        }

    }

    private void cargarDatos() {

        grupos = new ArrayList<String>();
        datosGrupos = new HashMap<String, List<String>>();

        grupos.add("Gestionar");
        grupos.add("Caja");
        grupos.add("Configuracion");


        List<String> hijos_grupo1 = new ArrayList<String>();
        hijos_grupo1.add("Socios");
        hijos_grupo1.add("Panel de Acceso");
        hijos_grupo1.add("Cobros");

        List<String> hijos_grupo2 = new ArrayList<String>();
        hijos_grupo2.add("Movimientos");

        List<String> hijos_grupo3 = new ArrayList<String>();
        hijos_grupo3.add("Usuarios");
        hijos_grupo3.add("Actividades");
        hijos_grupo3.add("Conceptos");
        hijos_grupo3.add("Articulos");

        datosGrupos.put(grupos.get(0), hijos_grupo1);
        datosGrupos.put(grupos.get(1), hijos_grupo2);
        datosGrupos.put(grupos.get(2), hijos_grupo3);


        adapter = new MyAdapter(this, grupos, datosGrupos);
        mDrawerExpandableList.setAdapter(adapter);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Fm_Inicio();
                mDrawerTitle = "Grupo 1";
                //mTitle = "Hijo 1 Grupo 1";
                break;
            case 1:
                fragment = new Fm_Inicio();
                break;
            case 2:
                fragment = new Fm_Inicio();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();


            mDrawerExpandableList.setItemChecked(position, true);
            mDrawerExpandableList.setSelection(position);
            setTitle(grupos.get(position));
            getSupportActionBar().setTitle(mDrawerTitle);
            //getSupportActionBar().setSubtitle(mTitle);
            mDrawerLayout.closeDrawer(mDrawerExpandableList);
        } else {
            Log.e("Aviso", "Error cuando se crea el fragment");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onKeyDown (int keycode, KeyEvent event){
        if (keycode == KeyEvent.KEYCODE_MENU) {

            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
            return true;
        }else{
            return super.onKeyDown(keycode, event);
        }
    }

    @Override
    public void onBackPressed() {

        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else{
            super.onBackPressed();
        }
    }
}