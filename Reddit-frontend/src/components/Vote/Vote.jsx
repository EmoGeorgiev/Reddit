import { useEffect, useState } from "react";
import { useAuth } from "../../hooks/useAuth";
import { VoteType } from "../../util/VoteType";
import voteService from "../../services/votes";
import UpVoteButton from "./UpVoteButton";
import DownVoteButton from "./DownVoteButton";

const Vote = ({ contentId, contentScore }) => {
  const [score, setScore] = useState(contentScore);
  const [voteType, setVoteType] = useState(VoteType.NO_VOTE);
  const { user, isAuthenticated } = useAuth();

  useEffect(() => {
    const getVote = async () => {
      try {
        const vote = await voteService.getVoteByContentAndUser(
          contentId,
          user.id
        );

        setVoteType(vote.voteType);
      } catch (error) {
        console.log(error);
      }
    };

    if (isAuthenticated) {
      getVote();
    }
  }, []);

  const toggleVote = async (type) => {
    try {
      const vote = {
        contentId,
        userId: user?.id,
        voteType: type,
      };

      const newVote = await voteService.toggleVote(vote);

      setVoteType(newVote.voteType);
      setScore((prevScore) => prevScore + newVote.score);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="p-1 flex justify-center space-x-1.5 bg-gray-200 rounded-full">
      <UpVoteButton
        handleVote={() => toggleVote(VoteType.UP_VOTE)}
        active={voteType === VoteType.UP_VOTE}
      />

      <span className="font-semibold">{score}</span>

      <DownVoteButton
        handleVote={() => toggleVote(VoteType.DOWN_VOTE)}
        active={voteType === VoteType.DOWN_VOTE}
      />
    </div>
  );
};

export default Vote;
