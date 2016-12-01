Rails.application.routes.draw do

  namespace :api, constraints: { format: 'json' } do
    resources :facebook do
      collection do
        get 'details'
        get 'birthdays'
        get 'user_interests'
        # get 'gift_recomendation'
      end
    end
  end
 
end
