import { useState } from 'react'
import CategoryTab from './CategoryTab'

const CategoryList = ({ defaultCategory, categories, categoryComponents }) => {
    const [currentCategory, setCurrentCategory] = useState(defaultCategory)
    
    const handleCategoryChange = (category) => {
        setCurrentCategory(category)
    }

    return (
        <div className='mx-48'>
            <div className='flex justify-center'>                    
                {categories.map(category => <CategoryTab key={category} 
                                                        category={category}
                                                        currentCategory={currentCategory} 
                                                        changeCategory={handleCategoryChange} />)}
            </div>
            
            <div className='mt-8 border-t border-gray-300'>
                {categoryComponents[currentCategory]}
            </div>
        </div>
    )
}

export default CategoryList