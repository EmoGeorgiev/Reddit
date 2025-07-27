const UpVoteButton = ({ handleVote, active }) => {
  return (
    <button onClick={handleVote}>
      <svg
        className="w-6 h-6 cursor-pointer"
        viewBox="0 0 24 24"
        stroke="currentColor"
        strokeWidth="1"
        xmlns="http://www.w3.org/2000/svg"
      >
        <polygon
          points="3 14 12 3 21 14 16 14 16 22 8 22 8 14 3 14"
          className={`${
            active ? "fill-red-600" : "fill-transparent"
          } hover:fill-red-700`}
        />
      </svg>
    </button>
  );
};

export default UpVoteButton;
