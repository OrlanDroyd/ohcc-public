package com.gmail.orlandroyd.ohcc.fragment.interfaces;

import com.gmail.orlandroyd.ohcc.fragment.AboutFragment;
import com.gmail.orlandroyd.ohcc.fragment.BookTabFragment;
import com.gmail.orlandroyd.ohcc.fragment.ContactFragment;
import com.gmail.orlandroyd.ohcc.fragment.GalleryFragment;
import com.gmail.orlandroyd.ohcc.fragment.GlossaryFragment;
import com.gmail.orlandroyd.ohcc.fragment.ListDirectivoFragment;
import com.gmail.orlandroyd.ohcc.fragment.ListEspecialistaFragment;
import com.gmail.orlandroyd.ohcc.fragment.ListSolicitudByCiudadanoFragment;
import com.gmail.orlandroyd.ohcc.fragment.ListSolicitudByEspecialistaFragment;
import com.gmail.orlandroyd.ohcc.fragment.MainFragment;
import com.gmail.orlandroyd.ohcc.fragment.MapFragment;
import com.gmail.orlandroyd.ohcc.fragment.ReportFragment;
import com.gmail.orlandroyd.ohcc.fragment.HelpFragment;

/**
 * Created by OrlanDroyd on 25/02/2019.
 */

public interface IFragments extends MainFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener,
        ContactFragment.OnFragmentInteractionListener,
        GalleryFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener,
        GlossaryFragment.OnFragmentInteractionListener,
        HelpFragment.OnFragmentInteractionListener,
        BookTabFragment.OnFragmentInteractionListener,
        ListEspecialistaFragment.OnFragmentInteractionListener,
        ListSolicitudByCiudadanoFragment.OnFragmentInteractionListener,
        ListDirectivoFragment.OnFragmentInteractionListener,
        ListSolicitudByEspecialistaFragment.OnFragmentInteractionListener,
        ReportFragment.OnFragmentInteractionListener {
}
