package it15ns.friendscom.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import it15ns.friendscom.fragments.ChatListFragment;
import it15ns.friendscom.fragments.ContactListFragment;
import it15ns.friendscom.fragments.PlaceholderFragment;

/**
 * Created by danie on 31/05/2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private ChatListFragment chatListFragment;
    private ContactListFragment contactListFragment;

    public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:
                if(chatListFragment== null)
                    chatListFragment = new ChatListFragment();
                return chatListFragment;

            case 1:
                if(contactListFragment== null)
                    contactListFragment = new ContactListFragment();
                return contactListFragment;

            default:
                return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Chat";
            case 1:
                return "Kontakte";
        }
        return null;
    }

    public void update() {
        chatListFragment.update();
        contactListFragment.update();
    }
}