import { VoteType } from "../../util/VoteType";

const VoteButton = ({ handleVote, active, voteType }) => {
  return (
    <button onClick={handleVote}>
      <svg
<<<<<<< HEAD
        className={`w-6 h-6 ${
          voteType === VoteType.DOWN_VOTE ? "transform rotate-180" : ""
        } cursor-pointer`}
=======
        className={`w-6 h-6 ${voteType === VoteType.DOWN_VOTE ? "transform rotate-180" : ""} cursor-pointer`}
>>>>>>> afcfc68540368602acfded0924f92145841fe822
        viewBox="0 0 24 24"
        stroke="currentColor"
        strokeWidth="1"
        xmlns="http://www.w3.org/2000/svg"
      >
        <polygon
          points="3 14 12 3 21 14 16 14 16 22 8 22 8 14 3 14"
          className={`${
            active
              ? voteType === VoteType.UP_VOTE
                ? "fill-red-600"
                : "fill-blue-600"
              : "fill-transparent"
          } ${
            voteType === VoteType.UP_VOTE
              ? "hover:fill-red-700"
              : "hover:fill-blue-700"
          } `}
        />
      </svg>
    </button>
  );
};

export default VoteButton;
