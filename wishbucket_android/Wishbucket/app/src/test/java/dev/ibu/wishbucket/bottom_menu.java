public class MainActivity extends AppCompatActivity {
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Notice how you don't use the setContentView method here! Just
        // pass your layout to bottom bar, it will be taken care of.
        // Everything will be just like you're used to.
        mBottomBar = BottomBar.bind(this, R.layout.activity_main,
                savedInstanceState);

        mBottomBar.setItems(
                new BottomBarTab(R.drawable.ic_recents, "Recents"),
                new BottomBarTab(R.drawable.ic_favorites, "Favorites"),
                new BottomBarTab(R.drawable.ic_nearby, "Nearby"),
                new BottomBarTab(R.drawable.ic_friends, "Friends")
        );

        mBottomBar.setOnItemSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onItemSelected(final int position) {
                // the user selected a new tab
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }
}