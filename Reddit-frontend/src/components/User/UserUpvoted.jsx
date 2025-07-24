import { usePagination } from '../../hooks/usePagination'
import EmptyContent from '../Common/EmptyContent'
import UserVoted from './UserVoted'
import voteService from '../../services/votes'

const UserUpvoted = ({ profile }) => {
    const { isEmpty, setIsEmpty } = usePagination()

    const getUpvotedPage = async (id, pageable) => {
        try {
            const upvotedPage = await voteService.getUpVotedByUserId(id, pageable)

            setIsEmpty(upvotedPage.empty)
            
            return upvotedPage
        } catch (error) {
            console.log(error)
        }
    }

    if (isEmpty) {
        return (
            <>
                <EmptyContent text={`u/${profile.username} hasn't upvoted yet`} />
            </>
        )
    }

    return (
        <>
            <UserVoted profile={profile} getVoted={getUpvotedPage} />
        </>
    )
}

export default UserUpvoted