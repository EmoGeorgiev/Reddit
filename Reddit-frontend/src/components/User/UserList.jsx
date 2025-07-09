import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import Pagination from '../Common/Pagination'
import userIcon from '../../assets/user-icon.svg'

const UserList = ({ query, getUsers }) => {
    const [users, setUsers] = useState([])
    const [page, setPage] = useState(0)
    const [isFirst, setIsFirst] = useState(true)
    const [isLast, setIsLast] = useState(true)

    useEffect(() => {
        const getUserPage = async () => {
            try {
                const userPage = await getUsers(query, { page })

                setUsers(userPage.content)
                setIsFirst(userPage.first)
                setIsLast(userPage.last)
            } catch (error) {
                console.log(error)
            }
        }

        getUserPage()
    }, [query, page])

    const handlePageChange = (change) => {
        setPage(page + change)
    }

    return (
        <div>
            <ul className='my-4'>
                {users.map(user => 
                        <li className='border-b border-gray-300' key={user.id}>
                            <Link to={`/users/${user.username}`}>
                                <div className='p-4 hover:bg-gray-100 rounded-2xl overflow-clip'>
                                    <div className='flex items-center space-x-2'>
                                        <img className='w-12 h-12' src={userIcon} alt='subreddit' />
                                        <h2 className='font-semibold'>
                                            u/{user.username}
                                        </h2>
                                    </div>
                                </div>
                            </Link>
                        </li>)}
            </ul>

            <Pagination handlePageChange={handlePageChange} isFirst={isFirst} isLast={isLast} />
        </div>
    )
}

export default UserList