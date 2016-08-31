package krp.com.photogallerydemo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class GalleryAlbumActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private GalleryAlbumAdapter mAdapter;

    private ArrayList<AlbumsModel> albumsModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_albums);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Album Thumbnail Images");
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new GridLayoutManager (this,2));

        // create an Object for Adapter
        mAdapter = new GalleryAlbumAdapter (GalleryAlbumActivity.this, getGalleryAlbumImages());

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        // set the recycler adapter item select listener
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(GalleryAlbumActivity.this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent galleryAlbumsIntent = new Intent (GalleryAlbumActivity.this, ShowAlbumImagesActivity.class);
                galleryAlbumsIntent.putExtra ("position",position);

                galleryAlbumsIntent.putExtra ("albumsList", getGalleryAlbumImages());
                startActivity(galleryAlbumsIntent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }

    private ArrayList<AlbumsModel> getGalleryAlbumImages() {
        if(albumsModels == null) {
            final String[] columns = {MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
            Cursor imagecursor = managedQuery(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                    null, null, orderBy + " DESC");
            albumsModels = Utils.getAllDirectoriesWithImages(imagecursor);
        }
        return albumsModels;
    }

}