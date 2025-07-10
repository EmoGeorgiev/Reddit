import { useState } from 'react'
import EmptyContent from '../Common/EmptyContent'

const UserUpvoted = ({ profile }) => {
    const [isEmpty, setIsEmpty] = useState(true)
    return (
        <div>
            {isEmpty && <EmptyContent text={`u/${profile.username} hasn't upvoted yet`} />}
        </div>
    )
}

export default UserUpvoted