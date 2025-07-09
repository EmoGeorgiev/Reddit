import { useEffect, useState } from 'react'
import { useAuth } from '../Authentication/AuthContext'
import { VoteType } from '../../util/VoteType'
import voteService from '../../services/votes'
import voteIcon from '../../assets/vote-icon.svg'

const Vote = ({ contentId, contentScore }) => {
    const [score, setScore] = useState(contentScore)
    const [voteType, setVoteType] = useState(VoteType.NO_VOTE)
    const { user, isAuthenticated } = useAuth()

    useEffect(() => {
        const getVote = async () => {
            try {
                const vote = await voteService.getVoteByContentAndUser(contentId, user.id)
                
                setVoteType(vote.voteType)
            } catch (error) {
                console.log(error)
            }
        }

        if (isAuthenticated) {
            getVote()
        }
    }, [])

    const toggleVote = async (type) => {
        try {
            const vote = { 
                contentId, 
                'userId': user?.id, 
                'voteType': type 
            }

            const newVote = await voteService.toggleVote(vote)

            setVoteType(newVote.voteType)
            setScore(score + newVote.score)
        } catch (error) {
            console.log(error)
        }
    }


    return (
        <div className='p-1 flex justify-center space-x-1.5 bg-gray-200 rounded-full'>
            <button className={`${voteType === VoteType.UP_VOTE && 'bg-red-600'} hover:bg-red-700 rounded-full cursor-pointer`} 
                    onClick={() => toggleVote(VoteType.UP_VOTE)}>
                <img className='w-6 h-6' src={voteIcon} alt='vote' />
            </button>

            <span className='font-semibold'>{score}</span>

            <button className={`${voteType === VoteType.DOWN_VOTE && 'bg-blue-600'} hover:bg-blue-700 rounded-full cursor-pointer`} 
                    onClick={() => toggleVote(VoteType.DOWN_VOTE)}>
                <img className='w-6 h-6 rotate-180' src={voteIcon} alt='vote' />
            </button>
            
        </div>
    )
}

export default Vote