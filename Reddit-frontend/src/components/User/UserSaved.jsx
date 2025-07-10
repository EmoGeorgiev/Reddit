import { useState } from 'react'
import EmptyContent from '../Common/EmptyContent'

const UserSaved = ({ profile }) => {
    const [isEmpty, setIsEmpty] = useState(true)
    return (
        <div>
            {isEmpty && <EmptyContent text={`u/${profile.username} hasn't saved yet`} />}
        </div>
    )
}

export default UserSaved