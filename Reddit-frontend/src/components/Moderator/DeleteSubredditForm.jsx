import FormHeader from "../Common/FormHeader";

const DeleteSubredditForm = ({ deleteSubreddit, handleClose }) => {
  const handleDeleteSubreddit = (e) => {
    e.preventDefault();

    if (window.confirm("Are you sure you want to delete this subreddit?")) {
      deleteSubreddit();
    }
  };

  return (
    <div className="active-form h-2/7">
      <FormHeader name="Delete subreddit" handleClose={handleClose} />

      <p className="mx-5 my-3 font-thin">
        Once you delete the subreddit, all posts and comments associated with it
        will be permanently removed.
      </p>

      <form onSubmit={handleDeleteSubreddit}>
        <div className="active-form-btn-container">
          <button
            className="my-2 active-form-cancel-btn focus-item"
            type="button"
            onClick={handleClose}
          >
            Cancel
          </button>

          <button
            className="my-2 bg-red-700 hover:bg-red-800 active-form-confirm-btn focus-item"
            type="submit"
          >
            Delete
          </button>
        </div>
      </form>
    </div>
  );
};

export default DeleteSubredditForm;
