import { useState } from 'react'
import EmptyContent from '../Common/EmptyContent'
import UserVoted from './UserVoted'
import voteService from '../../services/votes'

const UserUpvoted = ({ profile }) => {
    const [isEmpty, setIsEmpty] = useState(false)

    const getDownvotedPage = async (id, pageable) => {
        try {
            const downvotedPage = await voteService.getDownVotedByUserId(id, pageable)

            setIsEmpty(downvotedPage.empty)
            
            return downvotedPage
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div>
            {isEmpty && <EmptyContent text={`u/${profile.username} hasn't downvoted yet`} />}
            <UserVoted profile={profile} getVoted={getDownvotedPage} />
        </div>
    )
}

export default UserUpvoted