package com.compunet.stuforia;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Edwin on 15/02/2015.
 */
public class ViewPagerAdapterChecklist extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public
    ViewPagerAdapterChecklist(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Fragment tab1 = new ChecklistTab1();
            return tab1;
        }
        else if(position==1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Fragment tab2 = new ChecklistTab2();
            return tab2;
        }
        else if(position==2)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Fragment tab3 = new ChecklistTab3();
            return tab3;
        }
        else if(position==3)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Fragment tab4 = new ChecklistTab4();
            return tab4;
        }
        else if(position==4)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Fragment tab5 = new ChecklistTab5();
            return tab5;
        }
        else {
            Fragment tab6=new ChecklistTab6();
            return tab6;
        }
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}