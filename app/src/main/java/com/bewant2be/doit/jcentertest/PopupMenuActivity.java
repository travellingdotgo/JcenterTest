package com.bewant2be.doit.jcentertest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Toast;
import android.support.v7.widget.PopupMenu;

public class PopupMenuActivity extends Activity {
    PopupMenu popupMenu;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup_menu);

        popupMenu = new PopupMenu(this, findViewById(R.id.popupmenu_btn));
        menu = popupMenu.getMenu();

        // 通过代码添加菜单项
        menu.add(Menu.NONE, Menu.FIRST + 0, 0, "复制");
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "粘贴");

        // 通过XML文件添加菜单项
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.popupmenu, menu);

        //
        PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.news:
                        Toast.makeText(PopupMenuActivity.this, "新建",
                                Toast.LENGTH_LONG).show();
                        break;
                    case R.id.open:
                        Toast.makeText(PopupMenuActivity.this, "打开",
                                Toast.LENGTH_LONG).show();
                        break;
                    case Menu.FIRST + 0:
                        Toast.makeText(PopupMenuActivity.this, "复制",
                                Toast.LENGTH_LONG).show();
                        break;
                    case Menu.FIRST + 1:
                        Toast.makeText(PopupMenuActivity.this, "粘贴",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        };

        // 监听事件
        popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    public void popupmenu(View v) {
        popupMenu.show();
    }
}