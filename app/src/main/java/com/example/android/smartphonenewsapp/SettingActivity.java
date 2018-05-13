package com.example.android.smartphonenewsapp;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingActivity extends AppCompatActivity {

    public static final String LOG_TAG = SettingActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener, DatePickerDialog.OnDateSetListener {

        SharedPreferences preferences;
        Calendar mCalendar;
        private int mCurrentYear;
        private int mCurrentMonth;
        private int mCurrentDayOfMonth;
        private String mToday;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_main);


            Preference section = findPreference(getString(R.string.settings_choose_section_key));
            bindPreferenceSummaryToValue(section);

Preference orderBy = findPreference(getString(R.string.settings_order_by_key));

            Preference fromDatePref = findPreference(getString(R.string.settings_news_from_key));
            Preference toDatePref = findPreference(getString(R.string.settings_news_to_key));

            // Get today's Date
            mCalendar = Calendar.getInstance();
            mCurrentYear = mCalendar.get(Calendar.YEAR);
            mCurrentMonth = mCalendar.get(Calendar.MONTH);
            mCurrentDayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
            mToday = dateFormat.format(mCalendar.getTime());


            /** Set date picked from calendar as preferred date from*/
            fromDatePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(final Preference preference) {

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                            String datePickedFormatted = "";

                            // Set picked date on calendar; if launching for first time then it's set to today's date
                            mCalendar = Calendar.getInstance();
                            mCalendar.set(Calendar.YEAR, year);
                            mCalendar.set(Calendar.MONTH, monthOfYear);
                            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            Date datePicked = mCalendar.getTime();

                            datePickedFormatted = dateFormat.format(mCalendar.getTime());

                            if (datePickedFormatted.compareTo(mToday) > 0) {
                                Toast.makeText(getActivity(), getString(R.string.setting_future_date), Toast.LENGTH_LONG).show();
                            }

                            preference.setSummary(datePickedFormatted);
                            preferences.edit().putString(getString(R.string.settings_news_from_key), String.valueOf(datePicked.getTime())).commit();
                        }
                    }, mCurrentYear, mCurrentMonth, mCurrentDayOfMonth);

                    datePickerDialog.show();
                    return true;
                }
            });

            /** Set date picked from calendar as preferred date to*/
            toDatePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(final Preference preference) {

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                            String datePickedFormatted = "";

                            // Set picked date on calendar; if launching for first time then it's set to today's date
                            mCalendar = Calendar.getInstance();
                            mCalendar.set(Calendar.YEAR, year);
                            mCalendar.set(Calendar.MONTH, monthOfYear);
                            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            Date datePicked = mCalendar.getTime();

                            datePickedFormatted = dateFormat.format(mCalendar.getTime());

                            if (datePickedFormatted.compareTo(mToday) > 0) {
                                Toast.makeText(getActivity(), getString(R.string.setting_future_date), Toast.LENGTH_LONG).show();
                            }

                            preference.setSummary(datePickedFormatted);
                            preferences.edit().putString(getString(R.string.settings_news_to_key), String.valueOf(datePicked.getTime())).commit();
                        }
                    }, mCurrentYear, mCurrentMonth, mCurrentDayOfMonth);

                    datePickerDialog.show();
                    return true;
                }
            });

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }


        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            // Default method overridden from parent class
        }

    }
}
