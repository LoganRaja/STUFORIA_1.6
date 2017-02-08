package com.compunet.stuforia;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Edwin on 15/02/2015.
 */
public class ViewPagerAdapterTemplates extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapterTemplates(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Fragment tab1 = new TemplateTab1();
            return tab1;
        }
        else if(position == 1)           // As we are having 4 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Fragment tab2 = new TemplateTab2();
            return tab2;
        }else if(position == 2) // / As we are having 4 tabs if the position is now 1 it must be 2 so we are returning third tab
        {
            Fragment tab3 = new TemplateTab3();
            return tab3;
        }else if(position == 3) // / As we are having 4 tabs if the position is now 1 it must be 2 so we are returning third tab
        {
            Fragment tab4 = new TemplateTab4();
            return tab4;
        }
        else             // As we are having 2 tabs if the position is now 2 it must be 3 so we are returning fourth tab
        {
            Fragment tab5 = new TemplateTab5();
            return tab5;
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