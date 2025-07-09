import { useEffect, useState } from 'react'
import { useAuth } from '../Authentication/AuthContext'
import { SavedType } from '../../util/SavedType.js'
import savedContentService from '../../services/savedContents'
import saveIcon from '../../assets/save-icon.svg'

const Save = ({ contentId }) => {
    const [savedType, setSavedType] = useState(SavedType.NOT_SAVED)
    const { user, isAuthenticated } = useAuth()

    useEffect(() => {
        const getSaved = async () => {
            try {
                const saved = await savedContentService.getSavedByContentAndUser(contentId, user.id)
                
                setSavedType(saved.savedType)
            } catch (error) {
                console.log(error)
            }
        }

        if (isAuthenticated) {
            getSaved()
        }
    }, [])

    const toggleSaved = async () => {
        try {
            const saved = {
                'userId': user?.id,
                contentId,
                savedType
            }

            const newSaved = await savedContentService.toggleSavedContent(saved)
            
            setSavedType(newSaved.savedType)
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <button className='px-1.5 bg-gray-200 flex items-center hover:bg-gray-300 rounded-full cursor-pointer'
                onClick={toggleSaved}>
            <img className='w-8 h-8' src={saveIcon} alt='save' />
            <span className='font-semibold'>{savedType === SavedType.SAVED ? 'Unsave' : 'Save'}</span>
        </button>
    )
}

export default Save