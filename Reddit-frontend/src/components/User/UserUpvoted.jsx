import { useState } from 'react'
import EmptyContent from '../Common/EmptyContent'
import UserVoted from './UserVoted'
import voteService from '../../services/votes'

const UserUpvoted = ({ profile }) => {
    const [isEmpty, setIsEmpty] = useState(true)

    const getUpvotedPage = async (id, pageable) => {
        try {
            const upvotedPage = await voteService.getUpVotedByUserId(id, pageable)

            setIsEmpty(upvotedPage.empty)
            
            return upvotedPage
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div>
            {isEmpty && <EmptyContent text={`u/${profile.username} hasn't upvoted yet`} />}
            <UserVoted profile={profile} getVoted={getUpvotedPage} />
        </div>
    )
}

export default UserUpvoted