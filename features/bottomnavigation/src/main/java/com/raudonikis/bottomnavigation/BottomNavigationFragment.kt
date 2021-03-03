package com.raudonikis.bottomnavigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.raudonikis.bottomnavigation.databinding.FragmentBottomNavigationBinding
import com.raudonikis.navigation.BackButtonBehaviour
import com.raudonikis.navigation.setupWithNavController
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BottomNavigationFragment : Fragment(R.layout.fragment_bottom_navigation) {

    private val bottomNavSelectedItemIdKey = "BOTTOM_NAV_SELECTED_ITEM_ID_KEY"
    private var bottomNavSelectedItemId = R.id.navigation_discover // Must be your starting destination
    private val binding: FragmentBottomNavigationBinding by viewBinding()

    @Inject
    lateinit var navigationHandler: NavigationHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.apply {
            bottomNavSelectedItemId =
                savedInstanceState.getInt(bottomNavSelectedItemIdKey, bottomNavSelectedItemId)
        }
        setUpBottomNavigation()
    }

    // Needed to maintain correct state over rotations
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(bottomNavSelectedItemIdKey, bottomNavSelectedItemId)
        super.onSaveInstanceState(outState)
    }

    private fun setUpBottomNavigation() {
        binding.apply {
            val navGraphIds = listOf(R.navigation.navigation_home, R.navigation.navigation_discover)
            bottomNavView.selectedItemId = bottomNavSelectedItemId
            val controller = bottomNavView.setupWithNavController(
                fragmentManager = childFragmentManager,
                navGraphIds = navGraphIds,
                backButtonBehaviour = BackButtonBehaviour.POP_HOST_FRAGMENT,
                containerId = R.id.bottom_nav_container,
                firstItemId = R.id.navigation_discover, // Must be the same as bottomNavSelectedItemId
                intent = requireActivity().intent
            )

            controller.observe(viewLifecycleOwner, { navController ->
                bottomNavSelectedItemId =
                    navController.graph.id // Needed to maintain correct state on return
                navigationHandler.setNavigationController(navController)
            })
        }
    }
}