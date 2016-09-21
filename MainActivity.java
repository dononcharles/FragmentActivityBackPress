/** Import your library here **/

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener {


    public DrawerLayout drawer;
    public NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Toolbar **/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Drawer layout --- menu sidebar
         */
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * commit the first fragment Home
         */
        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, new HomeFragment());
            fragmentTransaction.addToBackStack("Ho").commit();
            // check first menu
            navigationView.getMenu().getItem(0).setChecked(true);
        }

    }

    @Override
    public void onFragmentInteraction(String title) {

    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 1) {
                fm.popBackStack();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, new HomeFragment()).commit();
                navigationView.getMenu().getItem(0).setChecked(true);
            }else {
                //super.onBackPressed();
                this.finish();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //to prevent current item select over and over
        if (item.isChecked()) {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        }

        Fragment fragment = null;
        String tag = null;

        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            tag = "Ho";
        } else if (id == R.id.nav_params) {
            fragment = new LoginFragment();
            tag = "Co";
        } 

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fTransction = fragmentManager.beginTransaction();

            if(fragmentManager.getBackStackEntryCount() == 1 && !"Ho".equals(tag)){
                fTransction.replace(R.id.content_frame, fragment);
                fTransction.addToBackStack(tag).commit();
            }else{
                fTransction.replace(R.id.content_frame, fragment).commit();
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
