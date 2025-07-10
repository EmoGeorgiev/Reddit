import { useState } from 'react'
import EmptyContent from '../Common/EmptyContent'

const UserDownvoted = ({ profile }) => {
    const [isEmpty, setIsEmpty] = useState(true)
    return (
        <div>
            {isEmpty && <EmptyContent text={`u/${profile.username} hasn't downvoted yet`} />}
        </div>
    )
}

export default UserDownvoted