class Api::FacebookController < Api::ApplicationController
  require 'koala'

  skip_before_action :verify_authenticity_token
  before_action :set_koala_graph


  def details
    profile = @graph.get_object("me")
    
    render json: profile
  end

  def birthdays
    friends = @graph.get_object("me?fields=friends")

    birthdays = {}

    render json: friends
  end

  def user_interests
    user_interests = @graph.get_connections(params[:fb_user_id], "likes")

    render json: user_interests
  end

  # def gift_recomendation
  #   chosen_interest = params[:interest]
    
  #   render json: recomended_gift
  # end


  private
    def set_koala_graph
      @ouath_token = params[:ouath_token]

      @graph = Koala::Facebook::API.new(@ouath_token)
    end
end
