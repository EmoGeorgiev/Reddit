const DownVoteButton = ({ handleVote, active }) => {
  return (
    <button onClick={handleVote}>
      <svg
        className="w-6 h-6"
        viewBox="0 0 24 24"
        stroke="currentColor"
        strokeWidth="1"
        xmlns="http://www.w3.org/2000/svg"
      >
        <polygon
          points="21 10 12 21 3 10 8 10 8 2 16 2 16 10 21 10"
          className={`${
            active ? "fill-blue-600" : "fill-transparent"
          } hover:fill-blue-700`}
        />
      </svg>
    </button>
  );
};

export default DownVoteButton;
