package krp.com.photogallerydemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ShowAlbumImagesAdapter extends RecyclerView.Adapter<ShowAlbumImagesAdapter.ViewHolder> {

    public Context mContext;
    private SparseBooleanArray selectedItems;
    private ArrayList<AlbumImages> mAlbumImages;

    public ShowAlbumImagesAdapter(Context context, ArrayList<AlbumImages> galleryImagesList) {
        this.selectedItems = new SparseBooleanArray();
        this.mAlbumImages = galleryImagesList;
        this.mContext = context;

    }

    // Create new views
    @Override
    public ShowAlbumImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_show_gallery_images, null));
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        Glide.with (mContext)
                .load("file://"+mAlbumImages.get (position).getAlbumImages())
                .centerCrop()
                .placeholder(R.drawable.image_loading)
                .crossFade()
                .into (viewHolder.imgAlbum);

        viewHolder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return mAlbumImages == null ? 0 : mAlbumImages.size();
    }




    /**
     * Indicates if the item at position position is selected
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    /**
     * Toggle the selection status of the item at a given position
     * @param position Position of the item to toggle the selection status for
     *                 @return boolean isSelection?
     */
    public boolean toggleSelection(int position) {
        boolean isSelection;
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
            isSelection = false;
        } else {
            selectedItems.put(position, true);
            isSelection = true;
        }
        notifyItemChanged(position);
        return isSelection;
    }

    /**
     * Clear the selection status for all items
     */
    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    /**
     * Count the selected items
     * @return Selected items count
     */
    public int getSelectedItemCount() {
        return selectedItems == null ? 0 : selectedItems.size();
    }

    /**
     * Indicates the list of selected items
     * @return List of selected items ids
     */
    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<> (selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout selectedOverlay;
        public ImageView imgAlbum;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            imgAlbum = (ImageView) itemLayoutView.findViewById(R.id.img_album);
            selectedOverlay = (RelativeLayout) itemView.findViewById(R.id.selected_overlay);
        }
    }

    // method to access in activity after updating selection
    public ArrayList<AlbumImages> getAlbumImagesList() {
        return mAlbumImages;
    }
}